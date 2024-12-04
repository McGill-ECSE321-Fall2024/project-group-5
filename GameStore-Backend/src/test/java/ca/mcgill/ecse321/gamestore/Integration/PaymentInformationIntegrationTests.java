package ca.mcgill.ecse321.gamestore.Integration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import ca.mcgill.ecse321.gamestore.dao.PaymentInformationRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PaymentInformationIntegrationTests {
    /*
     * @Autowired
     * private TestRestTemplate client;
     */

    @Autowired
    private PaymentInformationRepository paymentInformationRepo;

    /*
     * private static final String VALID_CARDHOLDER_NAME = "John Doe";
     * private static final int VALID_CARD_NUMBER = 123456789;
     * private static final String VALID_EXPIRATION_DATE = "12/25";
     * private static final int VALID_CVC = 123;
     * private static final CardType VALID_CARD_TYPE = CardType.Visa;
     * private static final int VALID_CUSTOMER_ACCOUNT_ID = 1;
     */

    @AfterAll
    public void clearDatabase() {
        paymentInformationRepo.deleteAll();
    }

    /*
     * @SuppressWarnings("null")
     * 
     * @Test
     * 
     * @Order(1)
     * public void testCreateValidPaymentInformation() {
     * // Arrange
     * PaymentInformationRequestDto paymentRequest = new
     * PaymentInformationRequestDto(
     * VALID_CARDHOLDER_NAME, VALID_CARD_NUMBER, VALID_EXPIRATION_DATE, VALID_CVC,
     * VALID_CARD_TYPE, VALID_CUSTOMER_ACCOUNT_ID);
     * 
     * // Act
     * ResponseEntity<PaymentInformationResponseDto> response =
     * client.postForEntity("/api/payment-information/create", paymentRequest,
     * PaymentInformationResponseDto.class);
     * 
     * // Assert
     * assertNotNull(response);
     * assertEquals(HttpStatus.CREATED, response.getStatusCode());
     * assertTrue(response.getBody().getId() > 0, "The ID should be positive.");
     * this.paymentInformationId = response.getBody().getId();
     * assertEquals(VALID_CARDHOLDER_NAME, response.getBody().getCardholderName());
     * assertEquals(VALID_CARD_NUMBER, response.getBody().getCardNumber());
     * assertEquals(VALID_EXPIRATION_DATE,
     * response.getBody().getExpirationDate().toString());
     * assertEquals(VALID_CVC, response.getBody().getCvc());
     * assertEquals(VALID_CARD_TYPE, response.getBody().getCardType());
     * assertEquals(VALID_CUSTOMER_ACCOUNT_ID,
     * response.getBody().getCustomerAccountId());
     * }
     * 
     * @SuppressWarnings("null")
     * 
     * @Test
     * 
     * @Order(2)
     * public void testGetValidPaymentInformationById() {
     * // Arrange
     * String url = String.format("/api/payment-information/get/%d",
     * this.paymentInformationId);
     * 
     * // Act
     * ResponseEntity<PaymentInformationResponseDto> response =
     * client.getForEntity(url, PaymentInformationResponseDto.class);
     * 
     * // Assert
     * assertNotNull(response);
     * assertEquals(HttpStatus.OK, response.getStatusCode());
     * assertEquals(this.paymentInformationId, response.getBody().getId());
     * assertEquals(VALID_CARDHOLDER_NAME, response.getBody().getCardholderName());
     * assertEquals(VALID_CARD_NUMBER, response.getBody().getCardNumber());
     * assertEquals(VALID_EXPIRATION_DATE,
     * response.getBody().getExpirationDate().toString());
     * assertEquals(VALID_CVC, response.getBody().getCvc());
     * assertEquals(VALID_CARD_TYPE, response.getBody().getCardType());
     * assertEquals(VALID_CUSTOMER_ACCOUNT_ID,
     * response.getBody().getCustomerAccountId());
     * }
     */
}
