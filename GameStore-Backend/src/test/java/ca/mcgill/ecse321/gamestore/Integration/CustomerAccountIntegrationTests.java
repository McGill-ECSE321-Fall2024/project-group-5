package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.model.Account;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.service.AccountService;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountListDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerAccountResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerAccountIntegrationTests {
        @Autowired
        private TestRestTemplate customer;

        @Autowired
        private CustomerAccountRepository customerRepository;

        private final String VALID_USERNAME = "example";
        private final String VALID_USERNAME2 = "example2";
        private final String VALID_NAME = "Example Name";
        private final String VALID_EMAIL = "example@email.com";
        private final String VALID_EMAIL2 = "example2@email.com";
        private final String INVALID_EMAIL = "alice@.ca";
        private final String VALID_PASSWORD = "Password123!";
        private final String SALT = AccountService.generateSalt(8);
        private final String HASHED_VALID_PASSWORD = AccountService.hashPassword(VALID_PASSWORD, SALT);

        // private final String INVALID_PASSWORD = "123";
        private final int INVALID_ID = -1;

        private static final String CUSTOMER_ACCOUNT_API_URL = "/api/customer-account";
        private static final String CREATE_CUSTOMER_ACCOUNT_URL = CUSTOMER_ACCOUNT_API_URL + "/create";
        private static final String GET_CUSTOMER_ACCOUNT_BY_ID_URL = CUSTOMER_ACCOUNT_API_URL + "/get-with-id/";
        private static final String GET_CUSTOMER_ACCOUNT_BY_EMAIL_URL = CUSTOMER_ACCOUNT_API_URL + "/get-with-email/";
        private static final String UPDATE_CUSTOMER_ACCOUNT_USERNAME_URL = CUSTOMER_ACCOUNT_API_URL
                        + "/update-username/";
        private static final String UPDATE_CUSTOMER_ACCOUNT_PASSWORD_URL = CUSTOMER_ACCOUNT_API_URL
                        + "/update-password/";
        private static final String UPDATE_CUSTOMER_ACCOUNT_NAME_URL = CUSTOMER_ACCOUNT_API_URL + "/update-name/";
        private static final String DELETE_CUSTOMER_ACCOUNT_URL = CUSTOMER_ACCOUNT_API_URL + "/delete/";
        private static final String GET_ALL_CUSTOMER_ACCOUNTS_URL = CUSTOMER_ACCOUNT_API_URL + "/get/all";
        private static final String GET_CUSTOMER_ACCOUNT_BY_USERNAME_URL = CUSTOMER_ACCOUNT_API_URL
                        + "/get-with-username/";

        @BeforeEach
        @AfterEach
        public void clearDatabase() {
                customerRepository.deleteAll();
                Account.accountsByUsername.clear();
        }

        @Test
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
                assertEquals(VALID_EMAIL, createdCustomerAccount.getEmailAddress(),
                                "Email address should match input email address");
                assertEquals(VALID_NAME, createdCustomerAccount.getName(), "Customer name should match input name");
                assertNotNull(createdCustomerAccount.getId(), "Id should not be null");
                assertTrue(createdCustomerAccount.getId() > 0,
                                "Response should have a positive ID.");

        }

        @Test
        public void testCreateValidCustomerAccountWithNoName() {
                // Arrange
                CustomerAccountRequestDto request = new CustomerAccountRequestDto(VALID_USERNAME,
                                VALID_PASSWORD,
                                VALID_EMAIL,
                                null);

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
                assertEquals(VALID_EMAIL, createdCustomerAccount.getEmailAddress(),
                                "Email address should match input email address");
                assertEquals(null, createdCustomerAccount.getName(), "Customer name should match input name");
                assertNotNull(createdCustomerAccount.getId(), "Id should not be null");
                assertTrue(createdCustomerAccount.getId() > 0,
                                "Response should have a positive ID.");

        }

        @Test
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
                assertEquals("Email is not valid", response.getBody(),
                                "Expected error message for invalid email format");
        }

        @Test
        public void testCreateCustomerAccountWithNoDigitPassword() {
                // Arrange: Create a request with an invalid email
                CustomerAccountRequestDto request = new CustomerAccountRequestDto(
                                VALID_USERNAME,
                                "invalidPassword",
                                VALID_EMAIL, // Invalid email format
                                VALID_NAME);

                // Act: Send POST request to create the account
                ResponseEntity<String> response = customer.postForEntity(CREATE_CUSTOMER_ACCOUNT_URL, request,
                                String.class);

                // Assert: Verify response status code is BAD_REQUEST
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(),
                                "Expected status code 400 (BAD_REQUEST)");
                assertEquals("Password must contain at least one digit", response.getBody(),
                                "Expected error message for invalid password format");
        }

        @Test
        public void testFindCustomerAccountById() {
                // Create & save a new customer account
                CustomerAccount newCustomerAccount = new CustomerAccount(VALID_USERNAME, HASHED_VALID_PASSWORD, SALT,
                                VALID_EMAIL, VALID_NAME);
                customerRepository.save(newCustomerAccount);

                // Retrieve the valid ID from the saved customer account
                int validId = customerRepository.findByEmailAddress(VALID_EMAIL).getId();

                // Set the URL for GET request
                String url = GET_CUSTOMER_ACCOUNT_BY_ID_URL + validId;

                // Send GET request to retrieve the customer account by ID
                ResponseEntity<CustomerAccountResponseDto> response = customer.getForEntity(url,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code 200 (OK)");

                CustomerAccountResponseDto retrievedAccount = response.getBody();
                assertNotNull(retrievedAccount, "Account should be present in the response");
                assertEquals(VALID_USERNAME, retrievedAccount.getUsername(), "Username should match");
                assertEquals(VALID_EMAIL, retrievedAccount.getEmailAddress(), "Email should match");
                assertEquals(VALID_NAME, retrievedAccount.getName(), "Name should match");
                assertEquals(validId, retrievedAccount.getId(), "Account ID should match the created account ID");
        }

        @Test
        public void testFindCustomerAccountByInvalidId() {
                // Arrange: Use a non-existent account ID
                String url = GET_CUSTOMER_ACCOUNT_BY_ID_URL + INVALID_ID;

                // Act: Send GET request to retrieve the account by invalid ID
                ResponseEntity<String> response = customer.getForEntity(url, String.class);

                // Assert: Verify response status code is NOT_FOUND
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Expected status code 404 (NOT_FOUND)");
                assertEquals("CustomerAccount not found with ID: " + INVALID_ID, response.getBody(),
                                "No account associated with this id exists");
        }

        @Test
        public void testFindCustomerAccountByEmail() {
                // Create & save a new customer account
                CustomerAccount newCustomerAccount = new CustomerAccount(VALID_USERNAME,
                                HASHED_VALID_PASSWORD, SALT,
                                VALID_EMAIL, VALID_NAME);
                customerRepository.save(newCustomerAccount);

                // Retrieve the valid ID from the saved customer account
                String validEmail = customerRepository.findByEmailAddress(VALID_EMAIL).getEmailAddress();

                // Set the URL for GET request
                String url = GET_CUSTOMER_ACCOUNT_BY_EMAIL_URL + validEmail;

                // Send GET request to retrieve the customer account by ID
                ResponseEntity<CustomerAccountResponseDto> response = customer.getForEntity(url,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code 200 (OK)");

                CustomerAccountResponseDto retrievedAccount = response.getBody();
                assertNotNull(retrievedAccount, "Account should be present in the response");
                assertEquals(VALID_USERNAME, retrievedAccount.getUsername(), "Username should match");
                assertEquals(VALID_EMAIL, retrievedAccount.getEmailAddress(), "Email should match");
                assertEquals(VALID_NAME, retrievedAccount.getName(), "Name should match");
        }

        @Test
        public void testFindCustomerAccountByUsername() {
                // Create & save a new customer account
                CustomerAccount newCustomerAccount = new CustomerAccount(VALID_USERNAME,
                                HASHED_VALID_PASSWORD, SALT,
                                VALID_EMAIL, VALID_NAME);
                customerRepository.save(newCustomerAccount);

                // Retrieve the valid ID from the saved customer account
                String validUsername = customerRepository.findByUsername(VALID_USERNAME).getUsername();

                // Set the URL for GET request
                String url = GET_CUSTOMER_ACCOUNT_BY_USERNAME_URL + validUsername;

                // Send GET request to retrieve the customer account by ID
                ResponseEntity<CustomerAccountResponseDto> response = customer.getForEntity(url,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code 200 (OK)");

                CustomerAccountResponseDto retrievedAccount = response.getBody();
                assertNotNull(retrievedAccount, "Account should be present in the response");
                assertEquals(VALID_USERNAME, retrievedAccount.getUsername(), "Username should match");
                assertEquals(VALID_EMAIL, retrievedAccount.getEmailAddress(), "Email should match");
                assertEquals(VALID_NAME, retrievedAccount.getName(), "Name should match");
        }

        @Test
        public void testGetAllCustomerAccounts() {
                // Create & save a new customer account
                CustomerAccount newCustomerAccount1 = new CustomerAccount(VALID_USERNAME,
                                HASHED_VALID_PASSWORD, SALT,
                                VALID_EMAIL, VALID_NAME);
                CustomerAccount newCustomerAccount2 = new CustomerAccount(VALID_USERNAME2,
                                HASHED_VALID_PASSWORD, SALT,
                                VALID_EMAIL2, VALID_NAME);
                customerRepository.save(newCustomerAccount1);
                customerRepository.save(newCustomerAccount2);

                // Send GET request to retrieve the customer account by ID
                ResponseEntity<CustomerAccountListDto> response = customer.getForEntity(GET_ALL_CUSTOMER_ACCOUNTS_URL,
                                CustomerAccountListDto.class);

                // Validate the response
                assertNotNull(response, "Response should not be null");
                assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code 200 (OK)");

                CustomerAccountListDto customerAccountsResponse = response.getBody();
                assertNotNull(customerAccountsResponse);
                List<CustomerAccountResponseDto> listOfCustomers = customerAccountsResponse.getCustomers();
                assertEquals(2, listOfCustomers.size());

                // check first account
                CustomerAccountResponseDto customerAccount1 = listOfCustomers.get(0);
                assertNotNull(customerAccount1, "Account should be present in the response");
                assertEquals(VALID_USERNAME, customerAccount1.getUsername(), "Username should match");
                assertEquals(VALID_EMAIL, customerAccount1.getEmailAddress(), "Email should match");
                assertEquals(VALID_NAME, customerAccount1.getName(), "Name should match");

                // check second account
                CustomerAccountResponseDto customerAccount2 = listOfCustomers.get(1);
                assertNotNull(customerAccount2, "Account should be present in the response");
                assertEquals(VALID_USERNAME2, customerAccount2.getUsername(), "Username should match");
                assertEquals(VALID_EMAIL2, customerAccount2.getEmailAddress(), "Email should match");
                assertEquals(VALID_NAME, customerAccount2.getName(), "Name should match");
        }

        @Test
        public void testUpdateCustomerAccountUsername() {
                // get request
                CustomerAccountRequestDto request = new CustomerAccountRequestDto(VALID_USERNAME,
                                VALID_PASSWORD,
                                VALID_EMAIL,
                                VALID_NAME);

                // get the response
                ResponseEntity<CustomerAccountResponseDto> response = customer.postForEntity(
                                CREATE_CUSTOMER_ACCOUNT_URL, request,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                CustomerAccountResponseDto createdClient = response.getBody();
                assertNotNull(createdClient);
                assertTrue(createdClient.getId() > 0, "Response should have a positive ID.");

                // Creata new Name
                String newUsername = "newUsername";

                // Set the url
                String url = UPDATE_CUSTOMER_ACCOUNT_USERNAME_URL + VALID_EMAIL + "/" + newUsername;

                // Get the response
                ResponseEntity<CustomerAccountResponseDto> responseAfterUpdate = customer.exchange(url, HttpMethod.PUT,
                                null,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(responseAfterUpdate);
                assertEquals(HttpStatus.OK, responseAfterUpdate.getStatusCode());
                CustomerAccountResponseDto updatedClient = responseAfterUpdate.getBody();
                assertNotNull(updatedClient);
                assertEquals(updatedClient.getEmailAddress(), VALID_EMAIL);
                assertNotNull(updatedClient.getId());
                assertTrue(updatedClient.getId() > 0, "Response should have a positive ID.");
                assertNotNull(customerRepository.findByEmailAddress(VALID_EMAIL));
                assertEquals(customerRepository.findByEmailAddress(VALID_EMAIL).getUsername(), newUsername);
                long count = StreamSupport.stream(customerRepository.findAll().spliterator(), false).count();
                assertEquals(1, count);
        }

        @Test
        public void testUpdateCustomerAccountName() {
                // get request
                CustomerAccountRequestDto request = new CustomerAccountRequestDto(VALID_USERNAME,
                                VALID_PASSWORD,
                                VALID_EMAIL,
                                VALID_NAME);

                // get the response
                ResponseEntity<CustomerAccountResponseDto> response = customer.postForEntity(
                                CREATE_CUSTOMER_ACCOUNT_URL, request,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                CustomerAccountResponseDto createdClient = response.getBody();
                assertNotNull(createdClient);
                assertTrue(createdClient.getId() > 0, "Response should have a positive ID.");

                // Creata new Name
                String newName = "New Name";

                // Set the url
                String url = UPDATE_CUSTOMER_ACCOUNT_NAME_URL + VALID_EMAIL + "/" + newName;

                // Get the response
                ResponseEntity<CustomerAccountResponseDto> responseAfterUpdate = customer.exchange(url, HttpMethod.PUT,
                                null,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(responseAfterUpdate);
                assertEquals(HttpStatus.OK, responseAfterUpdate.getStatusCode());
                CustomerAccountResponseDto updatedClient = responseAfterUpdate.getBody();
                assertNotNull(updatedClient);
                assertEquals(updatedClient.getEmailAddress(), VALID_EMAIL);
                assertNotNull(updatedClient.getId());
                assertTrue(updatedClient.getId() > 0, "Response should have a positive ID.");
                assertNotNull(customerRepository.findByEmailAddress(VALID_EMAIL));
                assertEquals(customerRepository.findByEmailAddress(VALID_EMAIL).getName(), newName);
                long count = StreamSupport.stream(customerRepository.findAll().spliterator(), false).count();
                assertEquals(1, count);
        }

        @Test
        public void testUpdateCustomerAccountPassword() {
                // get request
                CustomerAccountRequestDto request = new CustomerAccountRequestDto(VALID_USERNAME,
                                VALID_PASSWORD,
                                VALID_EMAIL,
                                VALID_NAME);

                // get the response
                ResponseEntity<CustomerAccountResponseDto> response = customer.postForEntity(
                                CREATE_CUSTOMER_ACCOUNT_URL, request,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(response);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                CustomerAccountResponseDto createdClient = response.getBody();
                assertNotNull(createdClient);
                assertTrue(createdClient.getId() > 0, "Response should have a positive ID.");

                // Creata new Name
                String newPassword = "NewPassword1!";

                // Set the url
                String url = UPDATE_CUSTOMER_ACCOUNT_PASSWORD_URL + VALID_EMAIL + "/" + VALID_PASSWORD + "/"
                                + newPassword;

                // Get the response
                ResponseEntity<CustomerAccountResponseDto> responseAfterUpdate = customer.exchange(url, HttpMethod.PUT,
                                null,
                                CustomerAccountResponseDto.class);

                // Validate the response
                assertNotNull(responseAfterUpdate);
                assertEquals(HttpStatus.OK, responseAfterUpdate.getStatusCode());
                CustomerAccountResponseDto updatedClient = responseAfterUpdate.getBody();
                assertNotNull(updatedClient);
                assertEquals(updatedClient.getEmailAddress(), VALID_EMAIL);
                assertNotNull(updatedClient.getId());
                assertTrue(updatedClient.getId() > 0, "Response should have a positive ID.");
                assertNotNull(customerRepository.findByEmailAddress(VALID_EMAIL));

                String salt = customerRepository.findByEmailAddress(VALID_EMAIL).getRandomPassword();
                assertEquals(customerRepository.findByEmailAddress(VALID_EMAIL).getPasswordHash(),
                                AccountService.hashPassword(newPassword, salt));
                long count = StreamSupport.stream(customerRepository.findAll().spliterator(), false).count();
                assertEquals(1, count);
        }

        @SuppressWarnings("null")
        @Test
        public void testDeleteCustomerAccount() {
                // Create & save a new client
                CustomerAccount createdCustomerAccount = new CustomerAccount(VALID_USERNAME, HASHED_VALID_PASSWORD,
                                SALT, VALID_EMAIL, VALID_NAME);
                customerRepository.save(createdCustomerAccount);

                // Get the response
                ResponseEntity<CustomerAccountListDto> response = customer.getForEntity(GET_ALL_CUSTOMER_ACCOUNTS_URL,
                                CustomerAccountListDto.class);

                // Validate the response
                assertNotNull(response);
                assertEquals(HttpStatus.OK, response.getStatusCode());
                CustomerAccountListDto customerResponse = response.getBody();
                assertTrue(customerResponse.getCustomers().size() == 1);
                assertTrue(customerResponse.getCustomers().get(0).getEmailAddress().equals(VALID_EMAIL));

                int validId = customerResponse.getCustomers().get(0).getId();

                // Set the url
                String urlToDelete = DELETE_CUSTOMER_ACCOUNT_URL + validId;

                // Delete the client
                customer.delete(urlToDelete);

                // Get the response
                ResponseEntity<CustomerAccountListDto> updatedResponse = customer
                                .getForEntity(GET_ALL_CUSTOMER_ACCOUNTS_URL, CustomerAccountListDto.class);

                // Validate the response
                assertNotNull(updatedResponse);
                assertEquals(HttpStatus.OK, updatedResponse.getStatusCode());
                CustomerAccountListDto updatedCustomerResponse = updatedResponse.getBody();
                assertTrue(updatedCustomerResponse.getCustomers().size() == 0);
        }
}