package ca.mcgill.ecse321.gamestore.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

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

    private final String VALID_DESCRIPTION = "Great game!";
    private final float VALID_RATING = 4.5f;
    private final int VALID_GAME_ID = 1;
    private final int VALID_CUSTOMER_ID = 1;
    private final Date VALID_DATE = Date.valueOf(LocalDate.now());
    private int validReviewId;

    @Test
    @Order(1)
    public void testCreateValidReview() {
        // Arrange
        ReviewRequestDto request = new ReviewRequestDto();
        request.setDate(VALID_DATE);
        request.setDescription(VALID_DESCRIPTION);
        request.setRating(VALID_RATING);
        request.setGameId(VALID_GAME_ID);
        request.setCustomerAccountId(VALID_CUSTOMER_ID);

        // Act
        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/reviews", request, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto createdReview = response.getBody();
        assertNotNull(createdReview);
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(VALID_RATING, createdReview.getRating());
        assertNotNull(createdReview.getId());
        assertTrue(createdReview.getId() > 0);

        this.validReviewId = createdReview.getId();
    }

    @Test
    @Order(2)
    public void testCreateInvalidReviewRating() {
        // Arrange
        ReviewRequestDto request = new ReviewRequestDto();
        request.setDate(VALID_DATE);
        request.setDescription(VALID_DESCRIPTION);
        request.setRating(6.0f); // Invalid rating
        request.setGameId(VALID_GAME_ID);
        request.setCustomerAccountId(VALID_CUSTOMER_ID);

        // Act
        ResponseEntity<String> response = client.postForEntity("/reviews", request, String.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Rating must be between 0 and 5"));
    }

    @Test
    @Order(3)
    public void testUpdateReviewLikeDislike() {
        // Arrange
        String url = "/reviews/" + this.validReviewId + "/update";
        ReviewRequestDto updateRequest = new ReviewRequestDto();
        updateRequest.setLikeCount(20);
        updateRequest.setDislikeCount(3);

        // Act
        client.put(url, updateRequest);

        // Fetch updated review
        ResponseEntity<ReviewResponseDto> response = client.getForEntity("/reviews/" + this.validReviewId, ReviewResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(20, updatedReview.getLikeCount());
        assertEquals(3, updatedReview.getDislikeCount());
    }

    @Test
    @Order(4)
    public void testGetReviewsByGameId() {
        // Arrange
        String url = "/reviews/game/" + VALID_GAME_ID;

        // Act
        ResponseEntity<ReviewResponseDto[]> response = client.getForEntity(url, ReviewResponseDto[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ReviewResponseDto> reviews = Arrays.asList(response.getBody());
        assertNotNull(reviews);
        assertTrue(reviews.size() > 0, "There should be at least one review for the game.");
        assertEquals(VALID_GAME_ID, reviews.get(0).getGameId());
    }

    @Test
    @Order(5)
    public void testGetReviewsByCustomerId() {
        // Arrange
        String url = "/reviews/customer/" + VALID_CUSTOMER_ID;

        // Act
        ResponseEntity<ReviewResponseDto[]> response = client.getForEntity(url, ReviewResponseDto[].class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ReviewResponseDto> reviews = Arrays.asList(response.getBody());
        assertNotNull(reviews);
        assertTrue(reviews.size() > 0, "There should be at least one review for the customer.");
        assertEquals(VALID_CUSTOMER_ID, reviews.get(0).getCustomerAccountId());
    }
}
