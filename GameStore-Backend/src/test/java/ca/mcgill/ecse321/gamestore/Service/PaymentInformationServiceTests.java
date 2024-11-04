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

import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.service.PaymentInformationService;
import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;

@SpringBootTest
public class PaymentInformationServiceTests {
    @Mock
    private PaymentInformationRepository repo;
    @InjectMocks
    private PaymentInformationService service;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidPaymentInformation() {
    }

    @Test
    public void testReadPaymentInformationByValidId() {
    }

    @Test
    public void testReadPaymentInformationByInvalidId() {
    }
}