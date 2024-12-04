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

    private final StaffAccount SA1 = new StaffAccount("user1", hashedPassword1, salt1);
    private final StaffAccount SA2 = new StaffAccount("user2", hashedPassword2, salt2);
    private final StaffAccount SA3 = new StaffAccount("user3", hashedPassword3, salt3);

    // Test data
    private static final int TEST_ID = 999;
    private static final String TEST_PASSWORD = "Password4!";
    private static final String TEST_USERNAME = "user4";

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
        // when(staffAccountService.checkUsernameAvailabilityStaffAccount(anyString()))
        // .thenAnswer((InvocationOnMock invocation) -> {
        // String username = invocation.getArgument(0);
        // // Return true if the username is NOT in the accountsByUsername map,
        // indicating
        // // it's available
        // return !Account.accountsByUsername.containsKey(username);
        // });

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

    @Test
    public void testCreateValidStaffAccount() {
        String errorMessage = "";
        StaffAccount createdStaffAccount = null;
        try {
            // Act
            createdStaffAccount = staffAccountService.createStaffAccount(TEST_USERNAME, TEST_PASSWORD);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }

        verify(staffAccountRepository, times(1)).save(any(StaffAccount.class));

        assertNotNull(createdStaffAccount);
        String createdSalt = createdStaffAccount.getRandomPassword();
        String createdHashPassword = createdStaffAccount.getPasswordHash();

        // Assert
        assertEquals("", errorMessage);
        assertEquals(createdHashPassword, createdStaffAccount.getPasswordHash());
        assertEquals(TEST_USERNAME, createdStaffAccount.getUsername());
        assertEquals(createdSalt, createdStaffAccount.getRandomPassword());
    }

    // @Test
    // public void testCreateStaffAccount() {
    // StaffAccount savedAccount = new StaffAccount();
    // savedAccount.setUsername("staff123");

    // when(staffAccountRepository.save(any(StaffAccount.class))).thenReturn(savedAccount);

    // StaffAccount result = staffAccountService.createStaffAccount("staff123",
    // "strongPassword123", "John Doe");

    // assertNotNull(result);
    // assertEquals("staff123", result.getUsername());
    // verify(staffAccountRepository, times(1)).save(any(StaffAccount.class));
    // }

    // @Test
    // public void testUpdatePassword() {
    // StaffAccount account = new StaffAccount();
    // int id = 2;

    // when(staffAccountRepository.findById(id)).thenReturn(java.util.Optional.of(account));
    // when(AccountService.isValidPassword("UpdatedPassword2!")).thenReturn("");
    // when(AccountService.generateSalt(8)).thenReturn("newSalt");
    // when(AccountService.hashPassword("UpdatedPassword2!",
    // "newSalt")).thenReturn("hashedPassword");

    // staffAccountService.updatePassword(1, "UpdatedPassword2!");

    // assertEquals("hashedPassword", account.getPasswordHash());
    // verify(staffAccountRepository, times(1)).save(account);
    // }

    // @Test
    // public void testDeleteStaffAccount() {
    // when(staffAccountRepository.existsById(1)).thenReturn(true);

    // staffAccountService.deleteStaffAccount(1);

    // verify(staffAccountRepository, times(1)).deleteById(1);
    // }

    // @Test
    // public void testGetStaffAccountById() {
    // StaffAccount account = new StaffAccount();
    // account.setUsername("staff123");
    // int id = 2;

    // when(staffAccountRepository.findById(id)).thenReturn(java.util.Optional.of(account));

    // StaffAccount result = staffAccountService.getStaffAccountById(1);

    // assertNotNull(result);
    // assertEquals("staff123", result.getUsername());
    // }

    // @Test
    // public void testGetStaffAccountByUsername() {
    // StaffAccount account = new StaffAccount();
    // account.setUsername("staff123");

    // when(staffAccountRepository.findStaffAccountByUsername("staff123")).thenReturn(account);

    // StaffAccount result =
    // staffAccountService.getStaffAccountByUsername("staff123");

    // assertNotNull(result);
    // assertEquals("staff123", result.getUsername());
    // }
}
