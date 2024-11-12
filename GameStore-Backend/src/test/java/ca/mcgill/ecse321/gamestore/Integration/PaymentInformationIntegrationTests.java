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
import ca.mcgill.ecse321.gamestore.model.PaymentInformation;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PaymentInformationIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    private final String VALID_NAME = "Alice";
    private final int VALID_CARD_NUMBER = 12345678;
    private final Date VALID_EXPIRATION_DATE = Date.valueOf("2025-12-31");
    private final int VALID_CVC = 123;
    private final int CUSTOMER_ACCOUNT_ID = 1;

    private int validPaymentInfoId;

    @Test
    @Order(1)
    public void testCreateValidPaymentInformation() {
        // Arrange
        PaymentInformationRequestDto request = new PaymentInformationRequestDto(
                VALID_NAME, VALID_CARD_NUMBER, VALID_EXPIRATION_DATE.toString(), VALID_CVC, 
                PaymentInformation.CardType.Visa, CUSTOMER_ACCOUNT_ID);
        
        // Act
        ResponseEntity<PaymentInformationResponseDto> response = client.postForEntity(
                "/api/paymentInformation/create", request, PaymentInformationResponseDto.class);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PaymentInformationResponseDto createdPaymentInfo = response.getBody();
        assertNotNull(createdPaymentInfo);
        assertEquals(VALID_NAME, createdPaymentInfo.getCardholderName());
        assertEquals(VALID_EXPIRATION_DATE, createdPaymentInfo.getExpirationDate());
        assertEquals(PaymentInformation.CardType.Visa, createdPaymentInfo.getCardType());
        assertTrue(createdPaymentInfo.getCustomerAccountId() > 0);

        this.validPaymentInfoId = createdPaymentInfo.getId();
    }

    @Test
    @Order(2)
    public void testReadPaymentInformationByValidId() {
        // Arrange
        String url = "/api/paymentInformation/get/" + this.validPaymentInfoId;
        
        // Act
        ResponseEntity<PaymentInformationResponseDto> response = client.getForEntity(
                url, PaymentInformationResponseDto.class);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PaymentInformationResponseDto retrievedPaymentInfo = response.getBody();
        assertNotNull(retrievedPaymentInfo);
        assertEquals(VALID_NAME, retrievedPaymentInfo.getCardholderName());
        assertEquals(VALID_EXPIRATION_DATE, retrievedPaymentInfo.getExpirationDate());
        assertEquals(PaymentInformation.CardType.Visa, retrievedPaymentInfo.getCardType());
    }

    @Test
    @Order(3)
    public void testUpdatePaymentInformation() {
        // Arrange
        PaymentInformationRequestDto updatedRequest = new PaymentInformationRequestDto(
                "Updated Name", VALID_CARD_NUMBER, VALID_EXPIRATION_DATE.toString(), 
                456, PaymentInformation.CardType.Mastercard, CUSTOMER_ACCOUNT_ID);
        String url = "/api/paymentInformation/update/" + this.validPaymentInfoId;
        
        // Act
        client.put(url, updatedRequest);
        
        // Verify Update
        ResponseEntity<PaymentInformationResponseDto> response = client.getForEntity(
                "/api/paymentInformation/get/" + this.validPaymentInfoId, PaymentInformationResponseDto.class);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PaymentInformationResponseDto updatedPaymentInfo = response.getBody();
        assertNotNull(updatedPaymentInfo);
        assertEquals("Updated Name", updatedPaymentInfo.getCardholderName());
        assertEquals(456, updatedPaymentInfo.getCvc());
        assertEquals(PaymentInformation.CardType.Mastercard, updatedPaymentInfo.getCardType());
    }

    @Test
    @Order(4)
    public void testDeletePaymentInformation() {
        // Arrange
        String url = "/api/paymentInformation/delete/" + this.validPaymentInfoId;
        
        // Act
        client.delete(url);
        
        // Verify Deletion
        ResponseEntity<PaymentInformationResponseDto> response = client.getForEntity(
                "/api/paymentInformation/get/" + this.validPaymentInfoId, PaymentInformationResponseDto.class);
        
        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}