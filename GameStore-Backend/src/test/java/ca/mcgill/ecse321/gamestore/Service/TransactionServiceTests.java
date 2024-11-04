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

import ca.mcgill.ecse321.gamestore.model.Transaction;
import ca.mcgill.ecse321.gamestore.service.TransactionService;
import ca.mcgill.ecse321.gamestore.dao.TransactionRepository;

@SpringBootTest
public class TransactionServiceTests {
    @Mock
    private TransactionRepository repo;
    @InjectMocks
    private TransactionService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidTransaction() {
    }

    @Test
    public void testReadTransactionByValidId() {
    }

    @Test
    public void testReadTransactionByInvalidId() {
    }
}