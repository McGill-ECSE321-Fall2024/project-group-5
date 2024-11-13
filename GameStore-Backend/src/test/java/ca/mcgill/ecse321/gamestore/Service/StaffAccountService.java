package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.service.StaffAccountService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class StaffAccountServiceTests {

    @Mock
    private StaffAccountRepository staffAccountRepository;

    @Mock
    private AccountService accountService; // For password handling

    @InjectMocks
    private StaffAccountService staffAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateStaffAccount() {
        when(staffAccountRepository.existsStaffAccountByUsername("johndoe")).thenReturn(false);
        when(accountService.isValidPassword("password123")).thenReturn(true);
        when(accountService.generateSalt(8)).thenReturn("randomSalt");
        when(accountService.hashPassword("password123", "randomSalt")).thenReturn("hashedPassword");

        StaffAccount savedAccount = new StaffAccount();
        savedAccount.setId(1);
        savedAccount.setUsername("johndoe");
        savedAccount.setName("John Doe");

        when(staffAccountRepository.save(any(StaffAccount.class))).thenReturn(savedAccount);

        StaffAccount result = staffAccountService.createStaffAccount("johndoe", "password123", "John Doe");

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("johndoe", result.getUsername());
        verify(staffAccountRepository, times(1)).save(any(StaffAccount.class));
    }

    @Test
    public void testUpdatePassword() {
        StaffAccount staffAccount = new StaffAccount();
        staffAccount.setId(1);
        staffAccount.setPasswordHash("oldPasswordHash");
        staffAccount.setSalt("oldSalt");

        when(staffAccountRepository.findById(1)).thenReturn(Optional.of(staffAccount));
        when(accountService.isValidPassword("newPassword")).thenReturn(true);
        when(accountService.generateSalt(8)).thenReturn("newSalt");
        when(accountService.hashPassword("newPassword", "newSalt")).thenReturn("newHashedPassword");

        staffAccountService.updatePassword(1, "newPassword");

        assertEquals("newHashedPassword", staffAccount.getPasswordHash());
        assertEquals("newSalt", staffAccount.getSalt());
        verify(staffAccountRepository, times(1)).save(staffAccount);
    }

    @Test
    public void testDeleteStaffAccount() {
        when(staffAccountRepository.existsById(1)).thenReturn(true);

        staffAccountService.deleteStaffAccount(1);

        verify(staffAccountRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetStaffAccountById() {
        StaffAccount staffAccount = new StaffAccount();
        staffAccount.setId(1);
        staffAccount.setUsername("johndoe");
        staffAccount.setName("John Doe");

        when(staffAccountRepository.findById(1)).thenReturn(Optional.of(staffAccount));

        StaffAccount result = staffAccountService.getStaffAccountById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("johndoe", result.getUsername());
        verify(staffAccountRepository, times(1)).findById(1);
    }

    @Test
    public void testGetStaffAccountByUsername() {
        StaffAccount staffAccount = new StaffAccount();
        staffAccount.setId(1);
        staffAccount.setUsername("johndoe");
        staffAccount.setName("John Doe");

        when(staffAccountRepository.findStaffAccountByUsername("johndoe")).thenReturn(staffAccount);

        StaffAccount result = staffAccountService.getStaffAccountByUsername("johndoe");

        assertNotNull(result);
        assertEquals("johndoe", result.getUsername());
        verify(staffAccountRepository, times(1)).findStaffAccountByUsername("johndoe");
    }
}
