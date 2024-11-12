package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.service.StaffAccountService;
import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;

public class StaffAccountServiceTests {

    @Mock
    private StaffAccountRepository repo;

    @InjectMocks
    private StaffAccountService service;

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

        StaffAccount savedAccount = new StaffAccount();
        savedAccount.setId(1);
        savedAccount.setName(name);
        savedAccount.setUsername(username);

        when(repo.existsStaffAccountByUsername(username)).thenReturn(false);
        when(repo.save(any(StaffAccount.class))).thenReturn(savedAccount);

        // Act
        StaffAccount result = service.createStaffAccount(username, password, name);

        // Assert
        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(username, result.getUsername());
        assertEquals(1, result.getId());

        verify(repo, times(1)).existsStaffAccountByUsername(username);
        verify(repo, times(1)).save(any(StaffAccount.class));
    }

    @Test
    public void testReadStaffAccountByValidId() {
        // Arrange
        StaffAccount mockAccount = new StaffAccount();
        mockAccount.setId(1);
        mockAccount.setName("Alice");
        mockAccount.setUsername("alice123");

        when(repo.findById(1)).thenReturn(Optional.of(mockAccount));

        // Act
        StaffAccount result = service.getStaffAccountById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Alice", result.getName());
        assertEquals("alice123", result.getUsername());

        verify(repo, times(1)).findById(1);
    }

    @Test
    public void testReadStaffAccountByInvalidId() {
        // Arrange
        when(repo.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getStaffAccountById(999);
        });

        assertEquals("Staff account with ID 999 not found", exception.getMessage());

        verify(repo, times(1)).findById(999);
    }
}
