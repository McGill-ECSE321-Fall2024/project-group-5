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

import ca.mcgill.ecse321.gamestore.model.Address;
import ca.mcgill.ecse321.gamestore.service.AddressService;
import ca.mcgill.ecse321.gamestore.dao.AddressRepository;

@SpringBootTest
public class AccountServiceTests {
    @Mock
    private AddressRepository repo;
    @InjectMocks
    private AddressService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidAddress() {
    }

    @Test
    public void testReadAddressByValidId() {
    }

    @Test
    public void testReadAddressByInvalidId() {
    }
}