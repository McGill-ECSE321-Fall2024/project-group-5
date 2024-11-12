package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;

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

import ca.mcgill.ecse321.gamestore.dto.StaffAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.StaffAccountResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class StaffAccountIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    private final String VALID_NAME = "John Doe";
    private final String VALID_USERNAME = "johndoe";
    private final String VALID_PASSWORD = "password123";
    private final String INVALID_USERNAME = "";
    private int validId;

    @Test
    @Order(1)
    public void testCreateValidStaffAccount() {
        // Arrange
        StaffAccountRequestDto request = new StaffAccountRequestDto();
        request.setName(VALID_NAME);
        request.setUsername(VALID_USERNAME);
        request.setPassword(VALID_PASSWORD);

        // Act
        ResponseEntity<StaffAccountResponseDto> response = client.postForEntity("/staffAccounts", request, StaffAccountResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StaffAccountResponseDto createdAccount = response.getBody();
        assertNotNull(createdAccount);
        assertEquals(VALID_NAME, createdAccount.getName());
        assertEquals(VALID_USERNAME, createdAccount.getUsername());
        assertNotNull(createdAccount.getId());
        assertTrue(createdAccount.getId() > 0);

        this.validId = createdAccount.getId();
    }

    @Test
    @Order(2)
    public void testCreateInvalidStaffAccount() {
        // Arrange
        StaffAccountRequestDto request = new StaffAccountRequestDto();
        request.setName(""); // Invalid name
        request.setUsername(INVALID_USERNAME); // Invalid username
        request.setPassword(VALID_PASSWORD);

        // Act
        ResponseEntity<String> response = client.postForEntity("/staffAccounts", request, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Username cannot be empty"));
    }

    @Test
    @Order(3)
    public void testReadStaffAccountByValidId() {
        // Arrange
        String url = "/staffAccounts/" + this.validId;

        // Act
        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url, StaffAccountResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StaffAccountResponseDto account = response.getBody();
        assertNotNull(account);
        assertEquals(VALID_NAME, account.getName());
        assertEquals(VALID_USERNAME, account.getUsername());
        assertEquals(this.validId, account.getId());
    }

    @Test
    @Order(4)
    public void testDeleteStaffAccount() {
        // Arrange
        String url = "/staffAccounts/" + this.validId;

        // Act
        client.delete(url);

        // Try fetching the deleted staff account
        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url, StaffAccountResponseDto.class);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
