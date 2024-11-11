package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dto.PaymentInformationRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentInformationResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PaymentInformationIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    private final String VALID_NAME = "Alice";
    private final long VALID_CARD_NUMBER = 1234567812345678L; // Example card number
    private final Date VALID_EXPIRATION_DATE = Date.valueOf("2025-12-31");
    private final int VALID_CVC = 123;
    private final int CUSTOMER_ACCOUNT_ID = 1; // Example customer account ID

    private int validId;

    @Test
    @Order(1)
    public void testCreateValidPaymentInformation() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                VALID_NAME, VALID_CARD_NUMBER, VALID_EXPIRATION_DATE, VALID_CVC, 
                PaymentInformation.CardType.Visa, CUSTOMER_ACCOUNT_ID);
        
        // Act
        ResponseEntity<PaymentInformationResponseDto> response =
        client.postForEntity("/api/paymentinformation/create",
        request, PaymentInformationResponseDto.class);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PaymentInformationResponseDto createdPaymentInformation = response.getBody();
        assertNotNull(createdPaymentInformation);
        assertEquals(VALID_NAME, createdPaymentInformation.getCardholderName());
        assertEquals(VALID_CARD_NUMBER, createdPaymentInformation.getCardNumber());
        assertEquals(VALID_EXPIRATION_DATE, createdPaymentInformation.getExpirationDate());
        assertEquals(VALID_CVC, createdPaymentInformation.getCvc());
        assertEquals(PaymentInformation.CardType.Visa, createdPaymentInformation.getCardType());
        assertTrue(createdPaymentInformation.getCustomerAccountId() > 0, 
                "Customer account ID should be positive.");

        this.validId = createdPaymentInformation.getCustomerAccountId();
    }

    @Test
    @Order(2)
    public void testReadPaymentInformationByValidId() {
        // Arrange
        String url = "/api/paymentinformation/" + this.validId;
        
        // Act
        ResponseEntity<PaymentInformationResponseDto> response =
        client.getForEntity(url, PaymentInformationResponseDto.class);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PaymentInformationResponseDto person = response.getBody();
        assertNotNull(person);
        assertEquals(VALID_NAME, person.getCardholderName());
        assertEquals(VALID_CARD_NUMBER, person.getCardNumber());
        assertEquals(VALID_EXPIRATION_DATE, person.getExpirationDate());
        assertEquals(VALID_CVC, person.getCvc());
        assertEquals(PaymentInformation.CardType.Visa, person.getCardType());
    }
}
