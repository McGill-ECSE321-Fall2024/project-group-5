package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dto.GameStoreObjectRequestDto;
import ca.mcgill.ecse321.gamestore.dto.GameStoreObjectResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameStoreObjectIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    private final String VALID_POLICY = "Return within 30 days.";
    private final String UPDATED_POLICY = "Exchange within 15 days.";
    private int validId;

    @Test
    @Order(1)
    public void testCreateValidGameStoreObject() {
        // Arrange
        GameStoreObjectRequestDto request = new GameStoreObjectRequestDto(VALID_POLICY);

        // Act
        ResponseEntity<GameStoreObjectResponseDto> response = client.postForEntity(
                "/api/gamestoreobjects/create",
                request,
                GameStoreObjectResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        GameStoreObjectResponseDto createdObject = response.getBody();
        assertNotNull(createdObject);
        assertEquals(VALID_POLICY, createdObject.getPolicy());
        assertNotNull(createdObject.getId());
        assertTrue(createdObject.getId() > 0);

        // Store the ID for further tests
        this.validId = createdObject.getId();
    }

    @Test
    @Order(2)
    public void testReadGameStoreObjectByValidId() {
        // Arrange
        String url = "/api/gamestoreobjects/" + this.validId;

        // Act
        ResponseEntity<GameStoreObjectResponseDto> response = client.getForEntity(
                url,
                GameStoreObjectResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameStoreObjectResponseDto object = response.getBody();
        assertNotNull(object);
        assertEquals(VALID_POLICY, object.getPolicy());
        assertEquals(this.validId, object.getId());
    }

    @Test
    @Order(3)
    public void testReadGameStoreObjectByInvalidId() {
        // Arrange
        String url = "/api/gamestoreobjects/999";

        // Act
        ResponseEntity<String> response = client.getForEntity(
                url,
                String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("GameStoreObject not found"));
    }

    @Test
    @Order(4)
    public void testUpdateGameStoreObject() {
        // Arrange
        String url = "/api/gamestoreobjects/update/" + this.validId;
        GameStoreObjectRequestDto request = new GameStoreObjectRequestDto(UPDATED_POLICY);

        // Act
        ResponseEntity<GameStoreObjectResponseDto> response = client.exchange(
                url,
                org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(request),
                GameStoreObjectResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameStoreObjectResponseDto updatedObject = response.getBody();
        assertNotNull(updatedObject);
        assertEquals(UPDATED_POLICY, updatedObject.getPolicy());
        assertEquals(this.validId, updatedObject.getId());
    }

    @Test
    @Order(5)
    public void testDeleteGameStoreObject() {
        // Arrange
        String url = "/api/gamestoreobjects/delete/" + this.validId;

        // Act
        ResponseEntity<Void> response = client.exchange(
                url,
                org.springframework.http.HttpMethod.DELETE,
                null,
                Void.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the object was deleted
        ResponseEntity<String> getResponse = client.getForEntity(
                "/api/gamestoreobjects/" + this.validId,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
