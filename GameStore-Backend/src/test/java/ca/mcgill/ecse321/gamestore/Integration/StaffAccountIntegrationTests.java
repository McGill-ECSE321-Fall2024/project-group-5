package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    private final String VALID_NAME = "Alice";
    private final String VALID_USERNAME = "alice123";
    private final String VALID_PASSWORD = "password123";
    private final String INVALID_USERNAME = "nonexistent_user";
    private int validId;

    @Test
    @Order(1)
    public void testCreateValidStaffAccount() {
        StaffAccountRequestDto request = new StaffAccountRequestDto();
        request.setName(VALID_NAME);
        request.setUsername(VALID_USERNAME);
        request.setPassword(VALID_PASSWORD);

        ResponseEntity<StaffAccountResponseDto> response = client.postForEntity("/staffAccounts", request, StaffAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StaffAccountResponseDto createdStaffAccount = response.getBody();
        assertNotNull(createdStaffAccount);
        assertEquals(VALID_NAME, createdStaffAccount.getName());
        assertEquals(VALID_USERNAME, createdStaffAccount.getUsername());
        assertNotNull(createdStaffAccount.getId());
        assertTrue(createdStaffAccount.getId() > 0);

        this.validId = createdStaffAccount.getId();
    }

    @Test
    @Order(2)
    public void testReadStaffAccountByValidId() {
        String url = "/staffAccounts/" + this.validId;

        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url, StaffAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StaffAccountResponseDto staffAccount = response.getBody();
        assertNotNull(staffAccount);
        assertEquals(VALID_NAME, staffAccount.getName());
        assertEquals(VALID_USERNAME, staffAccount.getUsername());
        assertEquals(this.validId, staffAccount.getId());
    }

    @Test
    @Order(3)
    public void testReadStaffAccountByUsername() {
        String url = "/staffAccounts/username/" + VALID_USERNAME;

        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url, StaffAccountResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        StaffAccountResponseDto staffAccount = response.getBody();
        assertNotNull(staffAccount);
        assertEquals(VALID_NAME, staffAccount.getName());
        assertEquals(VALID_USERNAME, staffAccount.getUsername());
    }

    @Test
    @Order(4)
    public void testGetAllStaffAccounts() {
        ResponseEntity<StaffAccountResponseDto[]> response = client.getForEntity("/staffAccounts", StaffAccountResponseDto[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<StaffAccountResponseDto> staffAccounts = Arrays.asList(response.getBody());
        assertNotNull(staffAccounts);
        assertTrue(staffAccounts.size() > 0);
    }

    @Test
    @Order(5)
    public void testDeleteStaffAccount()  {
        String url = "/staffAccounts/" + this.validId;

        client.delete(url);

        ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url, StaffAccountResponseDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
