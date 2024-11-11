package ca.mcgill.ecse321.gamestore.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;
import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationResponseDto;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;
import ca.mcgill.ecse321.gamestore.service.PaymentInformationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Optional;

@SpringBootTest
public class PaymentInformationServiceTests {

    @Mock
    private PaymentInformationRepository paymentInfoRepo;

    @Mock
    private CustomerAccountRepository customerAccountRepo;

    @InjectMocks
    private PaymentInformationService paymentInfoService;

    @Test
    public void testCreateValidPaymentInformation() {
        // Arrange
        long validCardNumber = 1234567890123456L; // This is a long card number.
        PaymentInformationRequestDto requestDto = new PaymentInformationRequestDto(
                "John Doe", validCardNumber, Date.valueOf("2025-12-31"), 123, CardType.Visa, 1);

        // Set up mock behavior for the repository save method
        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCardholderName(requestDto.getCardholderName());
        paymentInformation.setCardNumber((int) validCardNumber);  // Cast to int as per the repository requirements
        paymentInformation.setExpirationDate(requestDto.getExpirationDate());
        paymentInformation.setCvc(requestDto.getCvc());
        paymentInformation.setCardType(requestDto.getCardType());

        when(paymentInfoRepo.save(any(PaymentInformation.class))).thenReturn(paymentInformation);

        // Act
        PaymentInformationResponseDto createdPaymentInfo = paymentInfoService.createPaymentInformation(requestDto);

        // Assert
        assertNotNull(createdPaymentInfo);
        assertEquals(requestDto.getCardholderName(), createdPaymentInfo.getCardholderName());
        assertEquals((int) validCardNumber, createdPaymentInfo.getCardNumber());  // Assert after casting to int
        assertEquals(requestDto.getExpirationDate(), createdPaymentInfo.getExpirationDate());
        assertEquals(requestDto.getCvc(), createdPaymentInfo.getCvc());
        assertEquals(requestDto.getCardType(), createdPaymentInfo.getCardType());
        verify(paymentInfoRepo, times(1)).save(any(PaymentInformation.class));
    }

    @Test
    public void testCreatePaymentInformationWithInvalidCardNumber() {
        // Arrange: invalid card number (0 is not a valid card number)
        PaymentInformationRequestDto requestDto = new PaymentInformationRequestDto(
                "John Doe", 0, Date.valueOf("2025-12-31"), 123, CardType.Visa, 1);

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInfoService.createPaymentInformation(requestDto));
        assertEquals("Invalid card number.", e.getMessage());
    }

    @Test
    public void testCreatePaymentInformationWithExpiredDate() {
        // Arrange: expired card date
        PaymentInformationRequestDto requestDto = new PaymentInformationRequestDto(
                "John Doe", 1234567890123456L, Date.valueOf("2020-12-31"), 123, CardType.Visa, 1);

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInfoService.createPaymentInformation(requestDto));
        assertEquals("Expiration date is in the past.", e.getMessage());
    }

    @Test
    public void testCreatePaymentInformationWithInvalidCardType() {
        // Arrange: invalid card type (null)
        PaymentInformationRequestDto requestDto = new PaymentInformationRequestDto(
                "John Doe", 1234567890123456L, Date.valueOf("2025-12-31"), 123, null, 1);

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInfoService.createPaymentInformation(requestDto));
        assertEquals("Card type cannot be null.", e.getMessage());
    }

    @Test
    public void testReadPaymentInformationByValidId() {
        // Arrange: valid ID
        int id = 1;
        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setCardholderName("John Doe");
        paymentInformation.setCardNumber(123456789);
        paymentInformation.setExpirationDate(Date.valueOf("2025-12-31"));
        paymentInformation.setCvc(123);
        paymentInformation.setCardType(CardType.Visa);

        when(paymentInfoRepo.findById(id)).thenReturn(Optional.of(paymentInformation));

        // Act
        PaymentInformationResponseDto foundPaymentInfo = paymentInfoService.getPaymentInformationById(id);

        // Assert
        assertNotNull(foundPaymentInfo);
        assertEquals(paymentInformation.getCardholderName(), foundPaymentInfo.getCardholderName());
        assertEquals(paymentInformation.getCardNumber(), foundPaymentInfo.getCardNumber());
        assertEquals(paymentInformation.getExpirationDate(), foundPaymentInfo.getExpirationDate());
        assertEquals(paymentInformation.getCvc(), foundPaymentInfo.getCvc());
        assertEquals(paymentInformation.getCardType(), foundPaymentInfo.getCardType());
    }

    @Test
    public void testReadPaymentInformationByInvalidId() {
        // Arrange: invalid ID
        int id = 999;
        when(paymentInfoRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInfoService.getPaymentInformationById(id));
        assertEquals("Payment information not found for this ID.", e.getMessage());
    }

    @Test
    public void testDeletePaymentInformationByValidId() {
        // Arrange: valid ID
        int id = 1;
        PaymentInformation paymentInformation = new PaymentInformation();
        paymentInformation.setId(id);
        paymentInformation.setCardholderName("John Doe");

        when(paymentInfoRepo.findById(id)).thenReturn(Optional.of(paymentInformation));

        // Act
        paymentInfoService.deletePaymentInformation(id);

        // Assert: Verify that the repository's delete method is called
        verify(paymentInfoRepo, times(1)).delete(paymentInformation);
    }

    @Test
    public void testDeletePaymentInformationByInvalidId() {
        // Arrange: invalid ID
        int id = 999;
        when(paymentInfoRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInfoService.deletePaymentInformation(id));
        assertEquals("Payment information not found for this ID.", e.getMessage());
    }

    @Test
    public void testDeletePaymentInformationThatDoesNotExist() {
        // Arrange: invalid ID
        int id = 999;
        when(paymentInfoRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> paymentInfoService.deletePaymentInformation(id));
        assertEquals("Payment information not found for this ID.", e.getMessage());
    }
}
