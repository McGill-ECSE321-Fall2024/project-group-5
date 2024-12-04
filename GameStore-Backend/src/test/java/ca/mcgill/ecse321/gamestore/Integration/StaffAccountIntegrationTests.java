package ca.mcgill.ecse321.gamestore.Integration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class StaffAccountIntegrationTests {

    /*
     * private TestRestTemplate staffAccount;
     * 
     * @Autowired
     * private StaffAccountRepository staffRepository;
     * 
     * private final String VALID_USERNAME = "example";
     * private final String VALID_USERNAME2 = "example2";
     * private final String VALID_NAME = "Example Name";
     * private final String VALID_EMAIL = "example@email.com";
     * private final String VALID_EMAIL2 = "example2@email.com";
     * private final String INVALID_EMAIL = "alice@.ca";
     * private final String VALID_PASSWORD = "Password123!";
     * private final String SALT = AccountService.generateSalt(8);
     * private final String HASHED_VALID_PASSWORD =
     * AccountService.hashPassword(VALID_PASSWORD, SALT);
     * 
     * // private final String INVALID_PASSWORD = "123";
     * private final int INVALID_ID = -1;
     * 
     * private static final String Staff_ACCOUNT_API_URL = "/api/staff-account";
     * private static final String CREATE_STAFF_ACCOUNT_URL = Staff_ACCOUNT_API_URL
     * + "/create";
     * private static final String GET_STAFF_ACCOUNT_BY_ID_URL =
     * Staff_ACCOUNT_API_URL + "/get-with-id/";
     * private static final String UPDATE_STAFF_ACCOUNT_PASSWORD_URL =
     * Staff_ACCOUNT_API_URL
     * + "/update-password/";
     * private static final String DELETE_STAFF_ACCOUNT_URL = Staff_ACCOUNT_API_URL
     * + "/delete/";
     * private static final String GET_ALL_STAFF_ACCOUNTS_URL =
     * Staff_ACCOUNT_API_URL + "/get/all";
     * private static final String GET_STAFF_ACCOUNT_BY_USERNAME_URL =
     * Staff_ACCOUNT_API_URL
     * + "/get-with-username/";
     */

    /*
     * @BeforeEach
     * 
     * @AfterEach
     * public void clearDatabase() {
     * staffRepository.deleteAll();
     * Account.accountsByUsername.clear();
     * }
     */

    // @Test
    // public void testCreateValidStaffAccount() {
    // // Arrange
    // StaffAccountRequestDto request = new StaffAccountRequestDto(VALID_USERNAME,
    // VALID_PASSWORD);

    // // Act: Send POST request to create the account
    // ResponseEntity<StaffAccountResponseDto> response =
    // staffAccount.postForEntity(
    // CREATE_STAFF_ACCOUNT_URL,
    // request, StaffAccountResponseDto.class);

    // // Assert: Verify response status and data integrity
    // assertNotNull(response, "Response should not be null");
    // assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Expected status
    // code 201 (CREATED)");

    // StaffAccountResponseDto createdStaffAccount = response.getBody();
    // assertNotNull(createdStaffAccount, "Response body (created Staff account)
    // should not be null");
    // assertEquals(VALID_USERNAME, createdStaffAccount.getUsername(),
    // "Username should match input username");
    // assertNotNull(createdStaffAccount.getId(), "Id should not be null");
    // assertTrue(createdStaffAccount.getId() > 0,
    // "Response should have a positive ID.");
    // }

    // @Test
    // public void testFindStaffAccountById() {
    // // Create & save a new Staff account
    // StaffAccount newStaffAccount = new StaffAccount(VALID_USERNAME,
    // HASHED_VALID_PASSWORD, SALT);
    // staffRepository.save(newStaffAccount);

    // // Retrieve the valid ID from the saved Staff account
    // int validId =
    // staffRepository.findStaffAccountByUsername(VALID_USERNAME).getId();

    // // Set the URL for GET request
    // String url = GET_STAFF_ACCOUNT_BY_ID_URL + validId;

    // // Send GET request to retrieve the Staff account by ID
    // ResponseEntity<StaffAccountResponseDto> response =
    // staffAccount.getForEntity(url,
    // StaffAccountResponseDto.class);

    // // Validate the response
    // assertNotNull(response, "Response should not be null");
    // assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code
    // 200 (OK)");

    // StaffAccountResponseDto retrievedAccount = response.getBody();
    // assertNotNull(retrievedAccount, "Account should be present in the response");
    // assertEquals(VALID_USERNAME, retrievedAccount.getUsername(), "Username should
    // match");
    // assertEquals(validId, retrievedAccount.getId(), "Account ID should match the
    // created account ID");
    // }

    // @Test
    // @Order(1)
    // public void testCreateStaffAccount() {
    // StaffAccountRequestDto request = new StaffAccountRequestDto();
    // request.setUsername(VALID_USERNAME);
    // request.setPassword(VALID_PASSWORD);
    // request.setName(VALID_NAME);

    // ResponseEntity<StaffAccountResponseDto> response =
    // client.postForEntity("/staffAccounts", request,
    // StaffAccountResponseDto.class);

    // assertNotNull(response);
    // assertEquals(HttpStatus.OK, response.getStatusCode());
    // StaffAccountResponseDto createdAccount = response.getBody();
    // assertNotNull(createdAccount);
    // assertEquals(VALID_USERNAME, createdAccount.getUsername());
    // assertEquals(VALID_NAME, createdAccount.getName());
    // assertNotNull(createdAccount.getId());
    // this.validId = createdAccount.getId();
    // }

    // @Test
    // @Order(2)
    // public void testGetStaffAccountById() {
    // String url = "/staffAccounts/" + this.validId;

    // ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url,
    // StaffAccountResponseDto.class);

    // assertNotNull(response);
    // assertEquals(HttpStatus.OK, response.getStatusCode());
    // StaffAccountResponseDto account = response.getBody();
    // assertNotNull(account);
    // assertEquals(VALID_USERNAME, account.getUsername());
    // assertEquals(VALID_NAME, account.getName());
    // }

    // @Test
    // @Order(3)
    // public void testGetStaffAccountByUsername() {
    // String url = "/staffAccounts/username/" + VALID_USERNAME;

    // ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url,
    // StaffAccountResponseDto.class);

    // assertNotNull(response);
    // assertEquals(HttpStatus.OK, response.getStatusCode());
    // StaffAccountResponseDto account = response.getBody();
    // assertNotNull(account);
    // assertEquals(VALID_USERNAME, account.getUsername());
    // assertEquals(VALID_NAME, account.getName());
    // }

    // @Test
    // @Order(4)
    // public void testUpdatePassword() {
    // String url = "/staffAccounts/" + this.validId + "/updatePassword";

    // client.put(url, UPDATED_PASSWORD);

    // // Verify updatePassword works by ensuring no exception is thrown and
    // fetching
    // // the user
    // ResponseEntity<StaffAccountResponseDto> response =
    // client.getForEntity("/staffAccounts/" + this.validId,
    // StaffAccountResponseDto.class);
    // assertNotNull(response);
    // assertEquals(HttpStatus.OK, response.getStatusCode());
    // }

    // @Test
    // @Order(5)
    // public void testDeleteStaffAccount() {
    // String url = "/staffAccounts/" + this.validId;

    // client.delete(url);

    // ResponseEntity<StaffAccountResponseDto> response = client.getForEntity(url,
    // StaffAccountResponseDto.class);

    // assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    // }
}
