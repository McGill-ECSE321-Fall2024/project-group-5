package ca.mcgill.ecse321.gamestore.Integration;

import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationResponseDto;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentInformationIntegrationTests {

    @Autowired
    private PaymentInformationService paymentInformationService;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    private CustomerAccount testCustomerAccount;
    private PaymentInformation testPaymentInformation;

    @BeforeEach
    void setUp() {
        // Initialize the test CustomerAccount
        testCustomerAccount = new CustomerAccount();
        testCustomerAccount.setId(1);
        testCustomerAccount.setEmail("testcustomer@example.com");
        customerAccountRepository.save(testCustomerAccount);

        // Initialize the test PaymentInformation
        testPaymentInformation = new PaymentInformation("John Doe", 123456789, Date.valueOf("2025-12-31"), 123, PaymentInformation.CardType.Visa, testCustomerAccount);
        paymentInformationRepository.save(testPaymentInformation);
    }

    @Test
    @Transactional
    @Rollback
    void testCreatePaymentInformationIntegration() {
        PaymentInformationRequestDto requestDTO = new PaymentInformationRequestDTO(
                "Jane Smith", 987654321, Date.valueOf("2026-11-30"), 321, PaymentInformation.CardType.Mastercard, 1);

        // Creating the payment information
        PaymentInformationResponseDto responseDTO = paymentInformationService.createPaymentInformation(requestDTO);

        assertNotNull(responseDTO);
        assertEquals("Jane Smith", responseDTO.getCardholderName());
        assertEquals(987654321, responseDTO.getCardNumber());
        assertEquals(321, responseDTO.getCvc());
        assertEquals(1, responseDTO.getCustomerAccountId());

        // Verify that the payment information was actually saved in the database
        PaymentInformation savedPaymentInfo = paymentInformationRepository.findById(responseDTO.getId()).orElse(null);
        assertNotNull(savedPaymentInfo);
        assertEquals("Jane Smith", savedPaymentInfo.getCardholderName());
    }

    @Test
    @Transactional
    @Rollback
    void testGetPaymentInformationByIdIntegration() {
        // Test retrieving payment information by its ID
        PaymentInformationResponseDto responseDTO = PaymentInformationService.getPaymentInformationById(testPaymentInformation.getId());

        assertNotNull(responseDTO);
        assertEquals("John Doe", responseDTO.getCardholderName());
        assertEquals(123456789, responseDTO.getCardNumber());
        assertEquals(123, responseDTO.getCvc());
        assertEquals(testCustomerAccount.getId(), responseDTO.getCustomerAccountId());
    }

    @Test
    @Transactional
    @Rollback
    void testCreatePaymentInformationCustomerAccountNotFoundIntegration() {
        // Try to create payment information with a non-existing customer account
        PaymentInformationRequestDto requestDTO = new PaymentInformationRequestDto(
                "Jane Smith", 987654321, Date.valueOf("2026-11-30"), 321, PaymentInformation.CardType.Mastercard, 999);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentInformationService.createPaymentInformation(requestDTO);
        });

        assertEquals("Customer account not found", exception.getMessage());
    }

    @Test
    @Transactional
    @Rollback
    void testGetPaymentInformationByIdNotFoundIntegration() {
        // Try to get payment information by a non-existing ID
        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentInformationService.getPaymentInformationById(999);
        });

        assertEquals("Payment Information not found", exception.getMessage());
    }
}
