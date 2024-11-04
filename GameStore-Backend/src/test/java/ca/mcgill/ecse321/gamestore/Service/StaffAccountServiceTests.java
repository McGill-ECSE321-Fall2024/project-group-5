package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;
import ca.mcgill.ecse321.gamestore.service.StaffAccountService;
import ca.mcgill.ecse321.gamestore.dao.StaffAccountRepository;

@SpringBootTest
public class StaffAccountServiceTests {
    @Mock
    private StaffAccountRepository repo;
    @InjectMocks
    private StaffAccountService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidStaffAccount() {
    }

    @Test
    public void testReadStaffAccountByValidId() {
    }

    @Test
    public void testReadStaffAccountByInvalidId() {
    }
}