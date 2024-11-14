package ca.mcgill.ecse321.gamestore.Integration;

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

import ca.mcgill.ecse321.gamestore.dto.CustomerAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerAccountIntegrationTests {
        @Autowired
        private TestRestTemplate customer;

        private final String VALID_USERNAME = "Alice331";
        private final String VALID_NAME = "Alice";
        private final String VALID_EMAIL = "alice@mail.mcgill.ca";
        private final String INVALID_EMAIL = "alice@.ca";
        private final String VALID_PASSWORD = "password123";
        // private final String INVALID_PASSWORD = "123";
        private final int INVALID_ID = -1;
        private int validId;

        private static final String CUSTOMER_ACCOUNT_API_URL = "/api/customerAccount";
        private static final String CREATE_CUSTOMER_ACCOUNT_URL = CUSTOMER_ACCOUNT_API_URL + "/register";
        private static final String GET_CUSTOMER_ACCOUNT_URL = CUSTOMER_ACCOUNT_API_URL + "/getWithId/{id}";
        // private static final String UPDATE_CUSTOMER_ACCOUNT_URL =
        // CUSTOMER_ACCOUNT_API_URL + "update/{id}";
        // private static final String DELETE_CUSTOMER_ACCOUNT_URL =
        // CUSTOMER_ACCOUNT_API_URL + "delete/{id}";
        // private static final String GET_ALL_CUSTOMER_ACCOUNTS_URL =
        // CUSTOMER_ACCOUNT_API_URL + "get";
        // private static final String GET_CUSTOMER_ACCOUNT_BY_EMAIL_URL =
        // CUSTOMER_ACCOUNT_API_URL + "getWithEmail/{email}";
        // private static final String GET_CUSTOMER_ACCOUNT_BY_USERNAME_URL =
        // CUSTOMER_ACCOUNT_API_URL

        @Test
        @Order(1)
        public void testCreateValidCustomerAccount() {
                // Arrange
                CustomerAccountRequestDto request = new CustomerAccountRequestDto(VALID_USERNAME,
                                VALID_PASSWORD,
                                VALID_EMAIL,
                                VALID_NAME);

                // Act: Send POST request to create the account
                ResponseEntity<CustomerAccountResponseDto> response = customer.postForEntity(
                                CREATE_CUSTOMER_ACCOUNT_URL,
                                request, CustomerAccountResponseDto.class);

                // Assert: Verify response status and data integrity
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Expected status code 201 (CREATED)");

                CustomerAccountResponseDto createdCustomerAccount = response.getBody();
                assertNotNull(createdCustomerAccount, "Response body (created customer account) should not be null");
                assertEquals(VALID_USERNAME, createdCustomerAccount.getUsername(),
                                "Username should match input username");
                assertEquals(VALID_NAME, createdCustomerAccount.getName(), "Customer name should match input name");
                assertEquals(VALID_EMAIL, createdCustomerAccount.getEmailAddress(),
                                "Email address should match input email address");
                assertNotNull(createdCustomerAccount.getId(), "Id should not be null");
                assertTrue(createdCustomerAccount.getId() > 0,
                                "Response should have a positive ID.");

                this.validId = createdCustomerAccount.getId();

        }

        @Test
        @Order(2)
        public void testReadCustomerAccountByValidId() {
                // Ensure that a valid ID has been set in the previous test
                assertTrue(validId > 0, "Valid ID should be set after creating an account");

                // Arrange: Create the URL with the valid account ID
                String url = GET_CUSTOMER_ACCOUNT_URL + this.validId;

                // Act: Send GET request to retrieve the account by ID
                ResponseEntity<CustomerAccountResponseDto> response = customer.getForEntity(url,
                                CustomerAccountResponseDto.class);

                // Assert: Check response status and verify account details
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code 200 (OK)");

                CustomerAccountResponseDto retrievedAccount = response.getBody();
                assertNotNull(retrievedAccount, "Account should be present in the response");
                assertEquals(VALID_USERNAME, retrievedAccount.getUsername(), "Username should match");
                assertEquals(VALID_EMAIL, retrievedAccount.getEmailAddress(), "Email should match");
                assertEquals(VALID_NAME, retrievedAccount.getName(), "Name should match");
                assertEquals(this.validId, retrievedAccount.getId(), "Account ID should match the created account ID");
        }

        @Test
        @Order(3)
        public void testCreateCustomerAccountWithInvalidEmail() {
                // Arrange: Create a request with an invalid email
                CustomerAccountRequestDto request = new CustomerAccountRequestDto(
                                VALID_USERNAME,
                                VALID_PASSWORD,
                                INVALID_EMAIL, // Invalid email format
                                VALID_NAME);

                // Act: Send POST request to create the account
                ResponseEntity<String> response = customer.postForEntity(CREATE_CUSTOMER_ACCOUNT_URL, request,
                                String.class);

                // Assert: Verify response status code is BAD_REQUEST
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                                "Expected status code 400 (BAD_REQUEST)");
                assertEquals("Invalid email format", response.getBody(), "Expected error message for invalid email");
        }

        @Test
        @Order(4)
        public void testGetCustomerAccountByInvalidId() {
                // Arrange: Use a non-existent account ID
                String url = GET_CUSTOMER_ACCOUNT_URL + INVALID_ID;

                // Act: Send GET request to retrieve the account by invalid ID
                ResponseEntity<String> response = customer.getForEntity(url, String.class);

                // Assert: Verify response status code is NOT_FOUND
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Expected status code 404 (NOT_FOUND)");
        }

        @Test
        @Order(5)
        public void testUpdateCustomerAccount() {
                // Ensure a valid account is created first
                assertTrue(validId > 0, "Valid ID should be set after creating an account");

                // Arrange: Create the updated CustomerAccountRequestDto
                CustomerAccountRequestDto updatedRequest = new CustomerAccountRequestDto(
                                "updatedUsername",
                                "UpdatedPassword!2",
                                "updateduser@example.com",
                                "Updated User");

                String updateUrl = CUSTOMER_ACCOUNT_API_URL + "/update/" + validId;

                // Act: Send PUT request to update the account
                customer.put(updateUrl, updatedRequest);

                // Act: Retrieve the updated account
                ResponseEntity<CustomerAccountResponseDto> response = customer.getForEntity(
                                GET_CUSTOMER_ACCOUNT_URL + validId,
                                CustomerAccountResponseDto.class);

                // Assert: Verify the updated account data
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code 200 (OK)");

                CustomerAccountResponseDto updatedAccount = response.getBody();
                assertNotNull(updatedAccount, "Account response body should not be null");
                assertEquals("updatedUsername", updatedAccount.getUsername(), "Username should be updated");
                assertEquals("updateduser@example.com", updatedAccount.getEmailAddress(), "Email should be updated");
                assertEquals("Updated User", updatedAccount.getName(), "Name should be updated");
        }

        @Test
        @Order(6)
        public void testDeleteCustomerAccount() {
                // Ensure valid account ID is set
                assertTrue(validId > 0, "Valid ID should be set after creating an account");

                // Arrange: Create the delete URL
                String deleteUrl = CUSTOMER_ACCOUNT_API_URL + "/delete/" + validId;

                // Act: Send DELETE request to delete the account
                customer.delete(deleteUrl);

                // Act: Attempt to retrieve the deleted account
                ResponseEntity<String> response = customer.getForEntity(GET_CUSTOMER_ACCOUNT_URL + validId,
                                String.class);

                // Assert: Verify response status is NOT_FOUND
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Expected status code 404 (NOT_FOUND)");
        }
}