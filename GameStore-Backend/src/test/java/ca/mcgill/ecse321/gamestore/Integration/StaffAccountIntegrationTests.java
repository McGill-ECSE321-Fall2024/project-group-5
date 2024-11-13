package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.*;

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
    private final String UPDATED_PASSWORD = "newpassword456";
    private int validId;

    @Test
    @Order(1)
    public void testCreateStaffAccount() {
        StaffAccountRequestDto request = new StaffAccountRequestDto();
        request.setName(VALID_NAME);
        request.setUsername(VALID_USERNAME);
        request.setPassword(VALID_PASSWORD);

        ResponseEntity<StaffAccountResponseDto> response = client.postForEntity("/staffAccounts", request, StaffAccountResponseDto.class);

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
    public void testGetStaffAccountById() {
        String url = "/staffAccounts/" + this.validId;

        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url, StaffAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StaffAccountResponseDto account = response.getBody();
        assertNotNull(account);
        assertEquals(VALID_NAME, account.getName());
        assertEquals(VALID_USERNAME, account.getUsername());
        assertEquals(this.validId, account.getId());
    }

    @Test
    @Order(3)
    public void testGetStaffAccountByUsername() {
        String url = "/staffAccounts/username/" + VALID_USERNAME;

        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url, StaffAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StaffAccountResponseDto account = response.getBody();
        assertNotNull(account);
        assertEquals(VALID_NAME, account.getName());
        assertEquals(VALID_USERNAME, account.getUsername());
    }

    @Test
    @Order(4)
    public void testUpdatePassword() {
        String url = "/staffAccounts/" + this.validId + "/updatePassword";

        client.put(url, UPDATED_PASSWORD);

        // There’s no direct way to verify the password hash, so ensure no error occurs
        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity("/staffAccounts/" + this.validId, StaffAccountResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testDeleteStaffAccount() {
        String url = "/staffAccounts/" + this.validId;

        client.delete(url);

        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url, StaffAccountResponseDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
