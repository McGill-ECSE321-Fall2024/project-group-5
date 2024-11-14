package ca.mcgill.ecse321.gamestore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;

public class StaffAccountServiceTests {

    @Mock
    private StaffAccountRepository staffAccountRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private StaffAccountService staffAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

        @Test
    public void testCreateStaffAccount() {
        StaffAccount savedAccount = new StaffAccount();
        savedAccount.setUsername("staff123");
        

        when(staffAccountRepository.save(any(StaffAccount.class))).thenReturn(savedAccount);

        StaffAccount result = staffAccountService.createStaffAccount("staff123", "strongPassword123", "John Doe");

        assertNotNull(result);
        assertEquals("staff123", result.getUsername());
        verify(staffAccountRepository, times(1)).save(any(StaffAccount.class));
    }

    @Test
    public void testUpdatePassword() {
        StaffAccount account = new StaffAccount();
        int id = 2;
        
        when(staffAccountRepository.findById(id)).thenReturn(java.util.Optional.of(account));
        when(accountService.isValidPassword("UpdatedPassword2!")).thenReturn("");
        when(accountService.generateSalt(8)).thenReturn("newSalt");
        when(accountService.hashPassword("UpdatedPassword2!", "newSalt")).thenReturn("hashedPassword");

        staffAccountService.updatePassword(1, "UpdatedPassword2!");

        assertEquals("hashedPassword", account.getPasswordHash());
        verify(staffAccountRepository, times(1)).save(account);
    }

    @Test
    public void testDeleteStaffAccount() {
        when(staffAccountRepository.existsById(1)).thenReturn(true);

        staffAccountService.deleteStaffAccount(1);

        verify(staffAccountRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetStaffAccountById() {
        StaffAccount account = new StaffAccount();
        account.setUsername("staff123");
        int id =2 ;

        when(staffAccountRepository.findById(id)).thenReturn(java.util.Optional.of(account));

        StaffAccount result = staffAccountService.getStaffAccountById(1);

        assertNotNull(result);
        assertEquals("staff123", result.getUsername());
    }

    @Test
    public void testGetStaffAccountByUsername() {
        StaffAccount account = new StaffAccount();
        account.setUsername("staff123");

        when(staffAccountRepository.findStaffAccountByUsername("staff123")).thenReturn(account);

        StaffAccount result = staffAccountService.getStaffAccountByUsername("staff123");

        assertNotNull(result);
        assertEquals("staff123", result.getUsername());
    }
}
