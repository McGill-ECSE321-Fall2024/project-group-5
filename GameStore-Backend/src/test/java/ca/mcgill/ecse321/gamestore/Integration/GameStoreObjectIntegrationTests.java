package ca.mcgill.ecse321.gamestore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
@TestInstance(Lifecycle.PER_CLASS)
public class GameStoreObjectIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    private final String VALID_POLICY = "Return within 30 days.";
    private int validId;

    @Test
    @Order(1)
    public void testCreateValidGameStoreObject() {
        // Arrange
        GameStoreObjectRequestDto request = new GameStoreObjectRequestDto();
        request.setPolicy(VALID_POLICY);

        // Act
        ResponseEntity<GameStoreObjectResponseDto> response = client.postForEntity(
                "/gamestore-object/create",
                request,
                GameStoreObjectResponseDto.class
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        GameStoreObjectResponseDto createdGameStoreObject = response.getBody();
        assertNotNull(createdGameStoreObject);
        assertEquals(VALID_POLICY, createdGameStoreObject.getPolicy());
        assertNotNull(createdGameStoreObject.getId());
        assertTrue(createdGameStoreObject.getId() > 0, "Response should have a positive ID.");

        this.validId = createdGameStoreObject.getId();
    }

    @Test
    @Order(2)
    public void testReadGameStoreObjectByValidId() {
        // Arrange
        String url = "/gamestore-object/" + this.validId;

        // Act
        ResponseEntity<GameStoreObjectResponseDto> response = client.getForEntity(
                url,
                GameStoreObjectResponseDto.class
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GameStoreObjectResponseDto gameStoreObject = response.getBody();
        assertNotNull(gameStoreObject);
        assertEquals(VALID_POLICY, gameStoreObject.getPolicy());
        assertEquals(this.validId, gameStoreObject.getId());
    }

    @Test
    @Order(3)
    public void testReadGameStoreObjectByInvalidId() {
        // Arrange
        String url = "/gamestore-object/0";

        // Act
        ResponseEntity<String> response = client.getForEntity(
                url,
                String.class
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("GameStoreObject not found"));
    }
}
