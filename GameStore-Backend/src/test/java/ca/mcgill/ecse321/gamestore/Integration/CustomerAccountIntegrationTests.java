package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

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

import ca.mcgill.ecse321.gamestore.dto.CustomerAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerAccountIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    private final String VALID_NAME = "Alice";
    private final String VALID_EMAIL = "alice@mail.mcgill.ca";
    private final String VALID_PASSWORD = "password123";
    private final String INVALID_PASSWORD = "123";
    private final int INVALID_ID = 0;
    private int validId;

    @Test
    @Order(1)
    public void testCreateValidCustomerAccount() {
        /*
         * // Arrange
         * CustomerAccountRequestDto request = new CustomerAccountRequestDto(VALID_NAME,
         * VALID_EMAIL,
         * VALID_PASSWORD);
         * 
         * // Act
         * ResponseEntity<CustomerAccountResponseDto> response =
         * client.postForEntity("/people",
         * request, CustomerAccountResponseDto.class);
         * 
         * // Assert
         * assertNotNull(response);
         * assertEquals(HttpStatus.CREATED, response.getStatusCode());
         * CustomerAccountResponseDto createdCustomerAccount = response.getBody();
         * assertNotNull(createdCustomerAccount);
         * assertEquals(VALID_NAME, createdCustomerAccount.getName());
         * assertEquals(VALID_EMAIL, createdCustomerAccount.getEmail());
         * assertNotNull(createdCustomerAccount.getId());
         * assertTrue(createdCustomerAccount.getId() > 0,
         * "Response should have a positive ID.");
         * assertEquals(LocalDate.now(), createdCustomerAccount.getCreationDate());
         * 
         * this.validId = createdCustomerAccount.getId();
         */
    }

    @Test
    @Order(2)
    public void testReadCustomerAccountByValidId() {
        /*
         * // Arrange
         * String url = "/people/" + this.validId;
         * 
         * // Act
         * ResponseEntity<CustomerAccountResponseDto> response =
         * client.getForEntity(url,
         * CustomerAccountResponseDto.class);
         * 
         * // Assert
         * assertNotNull(response);
         * assertEquals(HttpStatus.OK, response.getStatusCode());
         * CustomerAccountResponseDto person = response.getBody();
         * assertNotNull(person);
         * assertEquals(VALID_NAME, person.getName());
         * assertEquals(VALID_EMAIL, person.getEmail());
         * assertEquals(this.validId, person.getId());
         * assertEquals(LocalDate.now(), person.getCreationDate());
         */
    }
}