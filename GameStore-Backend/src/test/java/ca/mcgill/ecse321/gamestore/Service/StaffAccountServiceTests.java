package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.service.StaffAccountService;
import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;
import ca.mcgill.ecse321.gamestore.dto.StaffAccountRequestDto;

@SpringBootTest
public class StaffAccountServiceTests {

    @Mock
    private StaffAccountRepository repo;

    @InjectMocks
    private StaffAccountService service;

    @Test
    public void testCreateValidStaffAccount() {
        StaffAccountRequestDto request = new StaffAccountRequestDto();
        request.setName("John Doe");
        request.setUsername("johndoe");
        request.setPassword("password123");

        StaffAccount savedAccount = new StaffAccount();
        savedAccount.setId(1);
        savedAccount.setName(request.getName());
        savedAccount.setUsername(request.getUsername());
        savedAccount.setPassword(request.getPassword());

        when(repo.existsStaffAccountByUsername("johndoe")).thenReturn(false);
        when(repo.save(any(StaffAccount.class))).thenReturn(savedAccount);

        StaffAccount result = service.createStaffAccount(request);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("johndoe", result.getUsername());
        assertEquals("password123", result.getPassword());
        assertEquals(1, result.getId());

        verify(repo, times(1)).existsStaffAccountByUsername("johndoe");
        verify(repo, times(1)).save(any(StaffAccount.class));
    }

    @Test
    public void testReadStaffAccountByValidId() {
        StaffAccount mockAccount = new StaffAccount();
        mockAccount.setId(1);
        mockAccount.setName("Alice");
        mockAccount.setUsername("alice123");
        mockAccount.setPassword("password123");

        when(repo.findById(1)).thenReturn(Optional.of(mockAccount));

        StaffAccount result = service.getStaffAccountById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Alice", result.getName());
        assertEquals("alice123", result.getUsername());

        verify(repo, times(1)).findById(1);
    }

    @Test
    public void testReadStaffAccountByInvalidId() {
        when(repo.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getStaffAccountById(999);
        });

        assertEquals("StaffAccount not found", exception.getMessage());

        verify(repo, times(1)).findById(999);
    }
}
