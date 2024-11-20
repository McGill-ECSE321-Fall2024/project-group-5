package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.Account;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.service.AccountService;
import ca.mcgill.ecse321.gamestore.service.CustomerAccountService;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;

@SpringBootTest
public class CustomerAccountServiceTests {
    @Mock
    private CustomerAccountRepository repo;
    @InjectMocks
    private CustomerAccountService service;

    @Mock
    private AccountService accountService;
    // Mock DB

    // Set up password and hashes
    String salt1 = AccountService.generateSalt(8);
    String salt2 = AccountService.generateSalt(8);
    String salt3 = AccountService.generateSalt(8);
    String password1 = "Password1!";
    String password2 = "Password2!";
    String password3 = "Password3!";
    String hashedPassword1 = AccountService.hashPassword(password1, salt1);
    String hashedPassword2 = AccountService.hashPassword(password2, salt2);
    String hashedPassword3 = AccountService.hashPassword(password3, salt3);

    private final CustomerAccount CA1 = new CustomerAccount("user1", hashedPassword1, salt1, "p1@gmail.com");
    private final CustomerAccount CA2 = new CustomerAccount("user2", hashedPassword2, salt2, "p2@gmail.com");
    private final CustomerAccount CA3 = new CustomerAccount("user3", hashedPassword3, salt3, "p3@gmail.com");

    // Test data
    private static final int TEST_ID = 999;
    private static final String TEST_EMAIL = "p4@gmail.com";
    private static final String TEST_PASSWORD = "Password4!";
    private static final String TEST_USERNAME = "user4";
    private static final String TEST_NAME = "John Doe";

    @BeforeEach
    public void setMockOutput() {
        // Mock findById based on predefined CustomerAccount instances
        when(repo.findById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            Integer id = invocation.getArgument(0);
            if (id.equals(CA1.getId()))
                return CA1;
            if (id.equals(CA2.getId()))
                return CA2;
            if (id.equals(CA3.getId()))
                return CA3;
            return null;
        });

        // Mock findByUsername using the unique usernames in mock data
        when(repo.findByUsername(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String username = invocation.getArgument(0);
            if (username.equals(CA1.getUsername()))
                return CA1;
            if (username.equals(CA2.getUsername()))
                return CA2;
            if (username.equals(CA3.getUsername()))
                return CA3;
            return null;
        });

        // Mock findByEmailAddress to simulate email uniqueness checks
        when(repo.findByEmailAddress(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String email = invocation.getArgument(0);
            if (email.equals(CA1.getEmailAddress()))
                return CA1;
            if (email.equals(CA2.getEmailAddress()))
                return CA2;
            if (email.equals(CA3.getEmailAddress()))
                return CA3;
            return null;
        });

        // Mock checkUsernameAvailability to reflect real behavior using
        // accountsByUsername map
        when(accountService.checkUsernameAvailability(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    String username = invocation.getArgument(0);
                    // Return true if the username is NOT in the accountsByUsername map, indicating
                    // it's available
                    return !Account.accountsByUsername.containsKey(username);
                });

        // Mock save to simply return the CustomerAccount passed in
        when(repo.save(any(CustomerAccount.class))).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        });

    }

    @BeforeEach
    public void setUp() {
        // Clear the map to avoid duplicate username errors
        Account.accountsByUsername.clear();
    }

    @AfterEach
    public void cleanUp() {
        // Clear the map to avoid duplicate username errors
        Account.accountsByUsername.clear();
    }

    @AfterEach
    public void resetMockDB() {
        CA1.setEmail("p1@gmail.com");
        CA1.setPasswordHash(hashedPassword1);
        CA1.setUsername("user1");
        CA1.setRandomPassword(salt1);
        CA1.setName(null);

        CA2.setEmail("p2@gmail.com");
        CA2.setPasswordHash(hashedPassword2);
        CA2.setUsername("user2");
        CA2.setRandomPassword(salt2);
        CA2.setName(null);

        CA3.setEmail("p3@gmail.com");
        CA3.setPasswordHash(hashedPassword3);
        CA3.setUsername("user3");
        CA3.setRandomPassword(salt3);
        CA3.setName(null);
    }

    @Test
    public void testReadCustomerAccountByValidId() {
        String errorMessage = "";
        CustomerAccount retrievedCustomerAccount = null;

        try {
            // Act
            retrievedCustomerAccount = service.getCustomerAccountByID(CA1.getId());
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Assert
        assertEquals("", errorMessage);
        assertNotNull(retrievedCustomerAccount);
        assertEquals(CA1, retrievedCustomerAccount);
    }

    @Test
    public void testReadCustomerAccountByInvalidId() {
        String errorMessage = "";
        CustomerAccount retrievedCustomerAccount = null;

        try {
            retrievedCustomerAccount = service.getCustomerAccountByID(TEST_ID);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        assertEquals("No account associated with this id exists", errorMessage);
        assertNull(retrievedCustomerAccount);
    }

    @Test
    public void testReadCustomerAccountByValidEmailAddress() {
        String errorMessage = "";
        CustomerAccount retrievedCustomerAccount = null;
        // Act
        try {
            retrievedCustomerAccount = service.getCustomerAccountByEmail(CA1.getEmailAddress());
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Assert
        assertEquals("", errorMessage);
        assertNotNull(retrievedCustomerAccount);
        assertEquals(CA1, retrievedCustomerAccount);
    }

    @Test
    public void testReadCustomerAccountByInvalidEmailAddress() {
        String errorMessage = "";
        CustomerAccount retrievedCustomerAccount = null;

        try {
            retrievedCustomerAccount = service.getCustomerAccountByEmail(TEST_EMAIL);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        assertEquals("No account associated with this email exists", errorMessage);
        assertNull(retrievedCustomerAccount);
    }

    @Test
    public void testReadCustomerAccountByValidUsername() {
        String errorMessage = "";
        CustomerAccount retrievedCustomerAccount = null;
        // Act
        try {
            retrievedCustomerAccount = service.getCustomerAccountByUsername(CA1.getUsername());
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Assert
        assertEquals("", errorMessage);
        assertNotNull(retrievedCustomerAccount);
        assertEquals(CA1, retrievedCustomerAccount);
    }

    @Test
    public void testReadCustomerAccountByInvalidUsername() {
        String errorMessage = "";
        CustomerAccount retrievedCustomerAccount = null;

        try {
            retrievedCustomerAccount = service.getCustomerAccountByUsername(TEST_USERNAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        assertEquals("No account associated with this username exists", errorMessage);
        assertNull(retrievedCustomerAccount);
    }

    @Test
    public void testGetAllCustomerAccounts() {
        String errorMessage = "";

        List<CustomerAccount> allRetrievedCustomerAccounts = null;
        // Act
        try {
            allRetrievedCustomerAccounts = service.getAllCustomerAccounts();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Assert
        assertEquals("", errorMessage);
        assertNotNull(allRetrievedCustomerAccounts);
    }

    @Test
    public void testCreateValidCustomerAccount() {
        String errorMessage = "";
        CustomerAccount createdCustomerAccount = null;
        try {
            // Act
            createdCustomerAccount = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        assertNotNull(createdCustomerAccount);
        String createdSalt = createdCustomerAccount.getRandomPassword();
        String createdHashPassword = createdCustomerAccount.getPasswordHash();

        // Assert
        assertEquals("", errorMessage);
        assertEquals(TEST_EMAIL, createdCustomerAccount.getEmailAddress());
        assertEquals(createdHashPassword, createdCustomerAccount.getPasswordHash());
        assertEquals(TEST_USERNAME, createdCustomerAccount.getUsername());
        assertEquals(createdSalt, createdCustomerAccount.getRandomPassword());
    }

    @Test
    public void testCreateAccount_DuplicateEmail() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, CA1.getEmailAddress(), TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Account associated with given email already exists", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_NullEmail() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, null, TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Email cannot be null", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_EmptyEmail() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, "", TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Email cannot be empty", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_InvalidEmail() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, "test", TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Email is not valid", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_InvalidEmail1() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, "test@.com", TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Email is not valid", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_InvalidEmail2() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, "test@test.", TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Email is not valid", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_InvalidEmail3() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, "@test.com", TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Email is not valid", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_nullPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, null);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password cannot be null", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_EmptyPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, "         ");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password cannot be empty", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_tooShortPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, "1234");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must be at least 8 characters long", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_noDigitPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, "hellohello");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must contain at least one digit", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_noSpecialCharacterPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, "hellohello1");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must contain at least one special character", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_noLettersPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, "12341234!");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must contain at least one letter", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_noLowerCasePassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, "TESTTEST!2");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must have at least one lower case letter", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_noUpperCasePassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, "testtest!2");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must have at least one upper case letter", errorMessage);
        assertNull(account);
    }

    @Test
    public void testCreateAccount_noSpacePassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.createCustomerAccount(TEST_USERNAME, TEST_EMAIL, "Test Test!2");
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must not contain a space", errorMessage);
        assertNull(account);
    }

    @Test
    public void testDeleteAccountWithValidId() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Delete account
        try {
            account = service.deleteCustomerAccount(CA1.getId());
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("", errorMessage);
        assertNotNull(account);
        assertEquals(CA1, account);
        assertEquals(false, repo.existsByEmailAddress(CA1.getEmailAddress()));
    }

    @Test
    public void testDeleteAccountWithInvalidId() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Delete account
        try {
            account = service.deleteCustomerAccount(TEST_ID);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("No account associated with this id exists", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccountWithValidId() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Delete account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, TEST_PASSWORD, TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        String salt = CA1.getRandomPassword();
        String hashedPassword = AccountService.hashPassword(TEST_PASSWORD, salt);

        // Check
        assertEquals("", errorMessage);
        assertNotNull(account);
        assertEquals(TEST_USERNAME, account.getUsername());
        assertEquals(hashedPassword, account.getPasswordHash());
        assertEquals(TEST_NAME, account.getName());
        assertEquals(CA1.getEmailAddress(), account.getEmailAddress());
    }

    @Test
    public void testUpdateCustomerAccountInvalidId() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Delete account
        try {
            account = service.updateCustomerAccount(TEST_ID, TEST_USERNAME, TEST_PASSWORD, TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("No account associated with this id exists", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_nullPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Delete account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, null, TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password cannot be null", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_emptyPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, "         ", TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password cannot be empty", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_tooShortPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, "1234", TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must be at least 8 characters long", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_noDigitPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, "hellohello", TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must contain at least one digit", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_noSpecialCharacterPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, "hellohello1", TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must contain at least one special character", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_noLettersPassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, "12341234!", TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must contain at least one letter", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_noLowerCasePassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, "TESTTEST!2", TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must have at least one lower case letter", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_noUpperCasePassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, "testtest!2", TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must have at least one upper case letter", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_noSpacePassword() {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), TEST_USERNAME, "Test Test!2", TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("Password must not contain a space", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateCustomerAccount_duplicateUsername() throws Exception {
        String errorMessage = "";
        CustomerAccount account = null;

        // Create account
        try {
            account = service.updateCustomerAccount(CA1.getId(), CA2.getUsername(), TEST_PASSWORD, TEST_NAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("An account with this username already exits", errorMessage);
        assertNull(account);
    }

}