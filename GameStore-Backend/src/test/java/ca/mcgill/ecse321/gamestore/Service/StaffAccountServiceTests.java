package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.model.Account;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.service.AccountService;
import ca.mcgill.ecse321.gamestore.service.StaffAccountService;

public class StaffAccountServiceTests {
    @Mock
    private StaffAccountRepository staffAccountRepository;

    @InjectMocks
    private StaffAccountService staffAccountService;

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
    String newPassword = "NewPassword1!";
    String hashedPassword1 = AccountService.hashPassword(password1, salt1);
    String hashedPassword2 = AccountService.hashPassword(password2, salt2);
    String hashedPassword3 = AccountService.hashPassword(password3, salt3);

    private final StaffAccount SA1 = new StaffAccount("user5", hashedPassword1, salt1);
    private final StaffAccount SA2 = new StaffAccount("user6", hashedPassword2, salt2);
    private final StaffAccount SA3 = new StaffAccount("user7", hashedPassword3, salt3);

    // Test data
    private static final int TEST_ID = 999;
    private static final String TEST_PASSWORD = "Password4!";
    private static final String TEST_USERNAME = "user8";

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void setMockOutput() {
        // Mock findById based on predefined StaffAccount instances
        when(staffAccountRepository.findStaffAccountById(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
            Integer id = invocation.getArgument(0);
            if (id.equals(SA1.getId()))
                return SA1;
            if (id.equals(SA2.getId()))
                return SA2;
            if (id.equals(SA3.getId()))
                return SA3;
            return null;
        });

        // Mock findByUsername using the unique usernames in mock data
        when(staffAccountRepository.findStaffAccountByUsername(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    String username = invocation.getArgument(0);
                    if (username.equals(SA1.getUsername()))
                        return SA1;
                    if (username.equals(SA2.getUsername()))
                        return SA2;
                    if (username.equals(SA3.getUsername()))
                        return SA3;
                    return null;
                });

        // Mock checkUsernameAvailability to reflect real behavior using
        // accountsByUsername map
        when(accountService.checkUsernameAvailability(anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    String username = invocation.getArgument(0);
                    // Return true if the username is NOT in the accountsByUsername map,
                    // indicating
                    // it's available
                    return !Account.accountsByUsername.containsKey(username);
                });

        // Mock save to simply return the StaffAccount passed in
        when(staffAccountRepository.save(any(StaffAccount.class))).thenAnswer((InvocationOnMock invocation) -> {
            return invocation.getArgument(0);
        });

        when(staffAccountRepository.save(any(StaffAccount.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

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
        SA1.setPasswordHash(hashedPassword1);
        SA1.setUsername("user1");
        SA1.setRandomPassword(salt1);

        SA2.setPasswordHash(hashedPassword2);
        SA2.setUsername("user2");
        SA2.setRandomPassword(salt2);

        SA3.setPasswordHash(hashedPassword3);
        SA3.setUsername("user3");
        SA3.setRandomPassword(salt3);

    }

    @Test
    public void testReadStaffAccountByValidId() {
        String errorMessage = "";
        StaffAccount retrievedStaffAccount = null;

        try {
            // Act
            retrievedStaffAccount = staffAccountService.getStaffAccountById(SA1.getId());
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Assert
        assertEquals("", errorMessage);
        assertNotNull(retrievedStaffAccount);
        assertEquals(SA1, retrievedStaffAccount);
    }

    @Test
    public void testReadStaffAccountByInvalidId() {
        String errorMessage = "";
        StaffAccount retrievedStaffAccount = null;

        try {
            retrievedStaffAccount = staffAccountService.getStaffAccountById(TEST_ID);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        assertEquals("No staff account associated with given id", errorMessage);
        assertNull(retrievedStaffAccount);
    }

    @Test
    public void testReadStaffAccountByValidUsername() {
        String errorMessage = "";
        StaffAccount retrievedStaffAccount = null;
        // Act
        try {
            retrievedStaffAccount = staffAccountService.getStaffAccountByUsername(SA1.getUsername());
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Assert
        assertEquals("", errorMessage);
        assertNotNull(retrievedStaffAccount);
        assertEquals(SA1, retrievedStaffAccount);
    }

    @Test
    public void testReadStaffAccountByInvalidUsername() {
        String errorMessage = "";
        StaffAccount retrievedStaffAccount = null;

        try {
            retrievedStaffAccount = staffAccountService.getStaffAccountByUsername(TEST_USERNAME);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        assertEquals("No staff account associated with given username", errorMessage);
        assertNull(retrievedStaffAccount);
    }

    @Test
    public void testGetAllStaffAccounts() {
        String errorMessage = "";

        List<StaffAccount> allRetrievedStaffAccounts = null;
        // Act
        try {
            allRetrievedStaffAccounts = staffAccountService.getAllStaffAccounts();
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Assert
        assertEquals("", errorMessage);
        assertNotNull(allRetrievedStaffAccounts);
    }

    // @Test
    // public void testCreateValidStaffAccount() {
    // String errorMessage = "";
    // StaffAccount createdStaffAccount = null;
    // try {
    // // Act
    // createdStaffAccount = staffAccountService.createStaffAccount(TEST_USERNAME,
    // TEST_PASSWORD);
    // } catch (Exception e) {
    // errorMessage = e.getMessage();
    // }

    // verify(staffAccountRepository, times(1)).save(any(StaffAccount.class));

    // assertNotNull(createdStaffAccount);
    // String createdSalt = createdStaffAccount.getRandomPassword();
    // String createdHashPassword = createdStaffAccount.getPasswordHash();

    // // Assert
    // assertEquals("", errorMessage);
    // assertEquals(createdHashPassword, createdStaffAccount.getPasswordHash());
    // assertEquals(TEST_USERNAME, createdStaffAccount.getUsername());
    // assertEquals(createdSalt, createdStaffAccount.getRandomPassword());
    // }

    @Test
    public void testDeleteAccountWithValidId() {
        String errorMessage = "";
        StaffAccount account = null;

        // Delete account
        try {
            account = staffAccountService.deleteStaffAccount(SA1.getId());
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("", errorMessage);
        assertNotNull(account);
        assertEquals(SA1, account);
        assertEquals(false, staffAccountRepository.existsStaffAccountByUsername(SA1.getUsername()));
    }

    @Test
    public void testDeleteAccountWithInvalidId() {
        String errorMessage = "";
        StaffAccount account = null;

        // Delete account
        try {
            account = staffAccountService.deleteStaffAccount(TEST_ID);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("No account associated with this id exists", errorMessage);
        assertNull(account);
    }

    @Test
    public void testUpdateStaffAccountPassword() {
        String errorMessage = "";
        StaffAccount updatedAccount = null;

        try {
            updatedAccount = staffAccountService.updateStaffAccountPassword(SA1.getUsername(), password1,
                    TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        // Check
        assertEquals("", errorMessage);
        assertNotNull(updatedAccount);
        String salt = updatedAccount.getRandomPassword();
        String hashedNewPassword = accountService.hashPassword(TEST_PASSWORD, salt);
        assertEquals(hashedNewPassword, updatedAccount.getPasswordHash());
    }

}
