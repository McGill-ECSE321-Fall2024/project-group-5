package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

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

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewIntegrationTests {
    @Autowired
    private TestRestTemplate client;

    private final String VALID_NAME = "Alice";
    private final String VALID_EMAIL = "alice@mail.mcgill.ca";
    private final String VALID_PASSWORD = "password123";
    private final String INVALID_PASSWORD = "123";
    private final int INVALID_ID = 0;
    private int validId;

    @Test
    @Order(1)
    public void testCreateValidReview() {
        /*
         * // Arrange
         * ReviewRequestDto request = new ReviewRequestDto(VALID_NAME, VALID_EMAIL,
         * VALID_PASSWORD);
         * 
         * // Act
         * ResponseEntity<ReviewResponseDto> response = client.postForEntity("/people",
         * request, ReviewResponseDto.class);
         * 
         * // Assert
         * assertNotNull(response);
         * assertEquals(HttpStatus.CREATED, response.getStatusCode());
         * ReviewResponseDto createdReview = response.getBody();
         * assertNotNull(createdReview);
         * assertEquals(VALID_NAME, createdReview.getName());
         * assertEquals(VALID_EMAIL, createdReview.getEmail());
         * assertNotNull(createdReview.getId());
         * assertTrue(createdReview.getId() > 0,
         * "Response should have a positive ID.");
         * assertEquals(LocalDate.now(), createdReview.getCreationDate());
         * 
         * this.validId = createdReview.getId();
         */
    }

    @Test
    @Order(2)
    public void testReadReviewByValidId() {
        /*
         * // Arrange
         * String url = "/people/" + this.validId;
         * 
         * // Act
         * ResponseEntity<ReviewResponseDto> response = client.getForEntity(url,
         * ReviewResponseDto.class);
         * 
         * // Assert
         * assertNotNull(response);
         * assertEquals(HttpStatus.OK, response.getStatusCode());
         * ReviewResponseDto person = response.getBody();
         * assertNotNull(person);
         * assertEquals(VALID_NAME, person.getName());
         * assertEquals(VALID_EMAIL, person.getEmail());
         * assertEquals(this.validId, person.getId());
         * assertEquals(LocalDate.now(), person.getCreationDate());
         */
    }
}