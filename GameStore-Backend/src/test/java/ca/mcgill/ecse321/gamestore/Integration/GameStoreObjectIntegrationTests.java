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
    private static int validId; // Shared across tests

    @Test
    @Order(1)
    public void testCreateValidGameStoreObject() {
        GameStoreObjectRequestDto request = new GameStoreObjectRequestDto(VALID_POLICY);

        ResponseEntity<GameStoreObjectResponseDto> response = client.postForEntity(
                "/api/game-store-object/create",
                request,
                GameStoreObjectResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        GameStoreObjectResponseDto createdObject = response.getBody();
        assertNotNull(createdObject);
        assertEquals(VALID_POLICY, createdObject.getPolicy());
        validId = createdObject.getId();
    }

    @SuppressWarnings("null")
    @Test
    @Order(2)
    public void testReadGameStoreObjectByValidId() {
        ResponseEntity<GameStoreObjectResponseDto> response = client.getForEntity(
                "/api/game-store-object/" + validId,
                GameStoreObjectResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(validId, response.getBody().getId());
        assertEquals(VALID_POLICY, response.getBody().getPolicy());
    }

    @SuppressWarnings("null")
    @Test
    @Order(3)
    public void testReadGameStoreObjectByInvalidId() {
        ResponseEntity<String> response = client.getForEntity("/api/game-store-object/999", String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("not found")); // Match backend error message
    }

    @SuppressWarnings("null")
    @Test
    @Order(4)
    public void testUpdateGameStoreObject() {
        GameStoreObjectRequestDto request = new GameStoreObjectRequestDto(UPDATED_POLICY);
        ResponseEntity<GameStoreObjectResponseDto> response = client.exchange(
                "/api/game-store-object/update/" + validId,
                org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(request),
                GameStoreObjectResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(UPDATED_POLICY, response.getBody().getPolicy());
    }

    @Test
    @Order(5)
    public void testDeleteGameStoreObject() {
        ResponseEntity<Void> response = client.exchange(
                "/api/game-store-object/delete/" + validId,
                org.springframework.http.HttpMethod.DELETE,
                null,
                Void.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEntity<String> getResponse = client.getForEntity("/api/game-store-object/" + validId, String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @Order(6)
    public void testInvalidUpdate() {
        GameStoreObjectRequestDto request = new GameStoreObjectRequestDto(UPDATED_POLICY);
        ResponseEntity<String> response = client.exchange(
                "/api/game-store-object/update/999",
                org.springframework.http.HttpMethod.PUT,
                new org.springframework.http.HttpEntity<>(request),
                String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("not found")); // Match backend error message
    }

    @SuppressWarnings("null")
    @Test
    @Order(7)
    public void testEmptyPolicy() {
        GameStoreObjectRequestDto request = new GameStoreObjectRequestDto("");

        ResponseEntity<String> response = client.postForEntity(
                "/api/game-store-object/create",
                request,
                String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Policy cannot be empty")); // Match backend validation error
    }
}
