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

    @InjectMocks
    private StaffAccountService staffAccountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateValidStaffAccount() {
        // Arrange
        String name = "John Doe";
        String username = "johndoe";
        String password = "password123";

        StaffAccount mockAccount = new StaffAccount();
        mockAccount.setId(1);
        mockAccount.setName(name);
        mockAccount.setUsername(username);

        when(staffAccountRepository.existsStaffAccountByUsername(username)).thenReturn(false);
        when(staffAccountRepository.save(any(StaffAccount.class))).thenReturn(mockAccount);

        // Act
        StaffAccount result = staffAccountService.createStaffAccount(username, password, name);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(username, result.getUsername());
        assertEquals(1, result.getId());
        verify(staffAccountRepository, times(1)).save(any(StaffAccount.class));
    }

    @Test
    public void testCreateDuplicateUsername() {
        // Arrange
        String username = "johndoe";

        when(staffAccountRepository.existsStaffAccountByUsername(username)).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            staffAccountService.createStaffAccount(username, "password123", "John Doe");
        });
        assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    public void testDeleteStaffAccount() {
        // Arrange
        int staffId = 1;

        when(staffAccountRepository.existsById(staffId)).thenReturn(true);

        // Act
        staffAccountService.deleteStaffAccount(staffId);

        // Assert
        verify(staffAccountRepository, times(1)).deleteById(staffId);
    }

    @Test
    public void testDeleteNonExistentStaffAccount()  {
        // Arrange
        int staffId = 999;

        when(staffAccountRepository.existsById(staffId)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            staffAccountService.deleteStaffAccount(staffId);
        });
        assertEquals("Staff account not found", exception.getMessage());
    }
}
