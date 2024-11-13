package ca.mcgill.ecse321.gamestore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import ca.mcgill.ecse321.gamestore.dto.PromotionCodeRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PromotionCodeResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PromotionCodeIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    private int validId;

    @Test
    @Order(1)
    public void testCreateValidPromotionCode() {
        // Arrange
        PromotionCodeRequestDto request = new PromotionCodeRequestDto();
        request.setCode("PROMO10");
        request.setDiscount(10);

        // Act
        ResponseEntity<PromotionCodeResponseDto> response = client.postForEntity("/promotion-code/create", request, PromotionCodeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PromotionCodeResponseDto createdPromotionCode = response.getBody();
        assertNotNull(createdPromotionCode);
        assertEquals("PROMO10", createdPromotionCode.getCode());
        assertEquals(10, createdPromotionCode.getDiscount());
        assertNotNull(createdPromotionCode.getId());
        assertTrue(createdPromotionCode.getId() > 0, "Response should have a positive ID.");

        this.validId = createdPromotionCode.getId();
    }

    @Test
    @Order(2)
    public void testReadPromotionCodeByValidId() {
        // Arrange
        String url = "/promotion-code/" + this.validId;

        // Act
        ResponseEntity<PromotionCodeResponseDto> response = client.getForEntity(url, PromotionCodeResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PromotionCodeResponseDto promotionCode = response.getBody();
        assertNotNull(promotionCode);
        assertEquals("PROMO10", promotionCode.getCode());
        assertEquals(10, promotionCode.getDiscount());
        assertEquals(this.validId, promotionCode.getId());
    }
}
