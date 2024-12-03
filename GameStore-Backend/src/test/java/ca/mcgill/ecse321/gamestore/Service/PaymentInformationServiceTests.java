package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Optional;

import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;
import ca.mcgill.ecse321.gamestore.service.PaymentInformationService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentInformationServiceTests {

    @Mock
    private PaymentInformationRepository paymentInformationRepository;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @InjectMocks
    private PaymentInformationService paymentInformationService;

    @Test
    public void testCreateValidPaymentInformation() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                "Alice", 12345678, "2025-12-31", 123, CardType.Visa, 1);
        
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setEmail("alice@example.com");
        customerAccount.setName("Alice");

        PaymentInformation paymentInformation = new PaymentInformation(
                "Alice", 12345678, Date.valueOf("2025-12-31"), 123, CardType.Visa, customerAccount);

        when(customerAccountRepository.findById(1)).thenReturn(customerAccount);
        when(paymentInformationRepository.save(any(PaymentInformation.class))).thenReturn(paymentInformation);

        // Act
        PaymentInformation createdPaymentInfo = paymentInformationService.createPaymentInformation(request);

        // Assert
        assertNotNull(createdPaymentInfo);
        assertEquals("Alice", createdPaymentInfo.getCardholderName());
        assertEquals(12345678, createdPaymentInfo.getCardNumber());
        assertEquals(Date.valueOf("2025-12-31"), createdPaymentInfo.getExpirationDate());
        assertEquals(123, createdPaymentInfo.getCvc());
        assertEquals(CardType.Visa, createdPaymentInfo.getCardType());
        verify(paymentInformationRepository, times(1)).save(any(PaymentInformation.class));
    }

    @Test
    public void testGetPaymentInformationByValidId() {
        // Arrange
        CustomerAccount customerAccount = new CustomerAccount();
        PaymentInformation paymentInfo = new PaymentInformation("Alice", 12345678, Date.valueOf("2025-12-31"), 123, CardType.Visa, customerAccount);

        when(paymentInformationRepository.findById(1)).thenReturn(Optional.of(paymentInfo));

        // Act
        PaymentInformation foundPaymentInfo = paymentInformationService.getPaymentInformationById(1);

        // Assert
        assertNotNull(foundPaymentInfo);
        assertEquals("Alice", foundPaymentInfo.getCardholderName());
        assertEquals(12345678, foundPaymentInfo.getCardNumber());
    }

    @Test
    public void testGetPaymentInformationByInvalidId() {
        // Arrange
        when(paymentInformationRepository.findById(999)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.getPaymentInformationById(999));
        assertEquals("No PaymentInformation with the specified ID exists.", e.getMessage());
    }

    @Test
    public void testUpdatePaymentInformation() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                "Updated Name", 87654321, "2026-01-01", 456, CardType.Mastercard, 1);
        
        CustomerAccount customerAccount = new CustomerAccount();
        PaymentInformation paymentInfo = new PaymentInformation("Alice", 12345678, Date.valueOf("2025-12-31"), 123, CardType.Visa, customerAccount);

        when(paymentInformationRepository.findById(1)).thenReturn(Optional.of(paymentInfo));
        when(paymentInformationRepository.save(any(PaymentInformation.class))).thenReturn(paymentInfo);

        // Act
        PaymentInformation updatedPaymentInfo = paymentInformationService.updatePaymentInformation(1, request);

        // Assert
        assertNotNull(updatedPaymentInfo);
        assertEquals("Updated Name", updatedPaymentInfo.getCardholderName());
        assertEquals(456, updatedPaymentInfo.getCvc());
        assertEquals(CardType.Mastercard, updatedPaymentInfo.getCardType());
        verify(paymentInformationRepository, times(1)).save(any(PaymentInformation.class));
    }

    @Test
    public void testDeletePaymentInformationByValidId() {
        // Arrange
        CustomerAccount customerAccount = new CustomerAccount();
        PaymentInformation paymentInfo = new PaymentInformation("Alice", 12345678, Date.valueOf("2025-12-31"), 123, CardType.Visa, customerAccount);

        when(paymentInformationRepository.findById(1)).thenReturn(Optional.of(paymentInfo));

        // Act
        paymentInformationService.deletePaymentInformation(1);

        // Assert
        verify(paymentInformationRepository, times(1)).delete(paymentInfo);
    }

    @Test
    public void testDeletePaymentInformationByInvalidId() {
        // Arrange
        when(paymentInformationRepository.findById(999)).thenReturn(Optional.empty());

        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.deletePaymentInformation(999));
        assertEquals("No PaymentInformation with the specified ID exists.", e.getMessage());
    }

    @Test
    public void testCreatePaymentInformationWithNullRequest() {
        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.createPaymentInformation(null));
        assertEquals("Request DTO cannot be null.", e.getMessage());
    }

    @Test
    public void testCreatePaymentInformationWithMissingFields() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                null, 12345678, "2025-12-31", 123, CardType.Visa, 1); // Missing cardholder name
    
        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.createPaymentInformation(request));
        assertEquals("Cardholder name cannot be null or empty.", e.getMessage());
    }

    @Test
    public void testCreatePaymentInformationWithInvalidCardNumber() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                "Alice", -1234, "2025-12-31", 123, CardType.Visa, 1); // Invalid card number
    
        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.createPaymentInformation(request));
        assertEquals("Card number must be a positive integer.", e.getMessage());
    }

    @Test
    public void testCreatePaymentInformationWithPastExpirationDate() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                "Alice", 12345678, "2020-01-01", 123, CardType.Visa, 1); // Past date
    
        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.createPaymentInformation(request));
        assertEquals("Expiration date must be in the future.", e.getMessage());
    }
    
    @Test
    public void testUpdateNonExistentPaymentInformation() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                "Updated Name", 87654321, "2026-01-01", 456, CardType.Mastercard, 1);
    
        when(paymentInformationRepository.findById(999)).thenReturn(Optional.empty());
    
        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.updatePaymentInformation(999, request));
        assertEquals("No PaymentInformation with the specified ID exists.", e.getMessage());
    }

    @Test
    public void testUpdatePaymentInformationWithNullRequest() {
        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.updatePaymentInformation(1, null));
        assertEquals("No PaymentInformation with the specified ID exists.", e.getMessage());
    }

    @Test
    public void testCreatePaymentInformationWithInvalidCardType() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                "Alice", 12345678, "2025-12-31", 123, null, 1); // Null card type
    
        // Act and Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInformationService.createPaymentInformation(request));
        assertEquals("Card type cannot be null.", e.getMessage());
    }
}
