package ca.mcgill.ecse321.gamestore.Integration;

import java.sql.Date;
import java.time.LocalDate;

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

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    private final String VALID_DESCRIPTION = "Amazing game!";
    private final float VALID_RATING = 4.5f;
    private final int VALID_LIKE_COUNT = 10;
    private final int VALID_DISLIKE_COUNT = 3;
    private final int VALID_GAME_ID = 1;
    private final int VALID_CUSTOMER_ID = 1;
    private final Date VALID_DATE = Date.valueOf(LocalDate.now());
    private int validReviewId;

    @Test
    @Order(1)
    public void testCreateReview() {
        ReviewRequestDto request = new ReviewRequestDto();
        request.setDate(VALID_DATE);
        request.setDescription(VALID_DESCRIPTION);
        request.setLikeCount(VALID_LIKE_COUNT);
        request.setDislikeCount(VALID_DISLIKE_COUNT);
        request.setRating(VALID_RATING);
        request.setCustomerAccountId(VALID_CUSTOMER_ID);
        request.setGameId(VALID_GAME_ID);

        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/reviews", request, ReviewResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto createdReview = response.getBody();
        assertNotNull(createdReview);
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(VALID_RATING, createdReview.getRating());
        assertEquals(VALID_LIKE_COUNT, createdReview.getLikeCount());
        assertEquals(VALID_DISLIKE_COUNT, createdReview.getDislikeCount());
        assertNotNull(createdReview.getId());

        this.validReviewId = createdReview.getId();
    }

    @Test
    @Order(2)
    public void testGetReviewById() {
        String url = "/reviews/" + this.validReviewId;

        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto review = response.getBody();
        assertNotNull(review);
        assertEquals(VALID_DESCRIPTION, review.getDescription());
        assertEquals(this.validReviewId, review.getId());
    }

    @Test
    @Order(3)
    public void testUpdateReview() {
        String url = "/reviews/" + this.validReviewId + "/update";

        ReviewRequestDto updateRequest = new ReviewRequestDto();
        updateRequest.setLikeCount(20);
        updateRequest.setDislikeCount(5);

        client.put(url, updateRequest);

        ResponseEntity<ReviewResponseDto> response = client.getForEntity("/reviews/" + this.validReviewId, ReviewResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto updatedReview = response.getBody();
        assertNotNull(updatedReview);
        assertEquals(20, updatedReview.getLikeCount());
        assertEquals(5, updatedReview.getDislikeCount());
    }

    @Test
    @Order(4)
    public void testGetReviewsByGameId() {
        String url = "/reviews/game/" + VALID_GAME_ID;

        ResponseEntity<ReviewResponseDto[]> response = client.getForEntity(url, ReviewResponseDto[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto[] reviews = response.getBody();
        assertNotNull(reviews);
        assertTrue(reviews.length > 0);

        for (ReviewResponseDto review : reviews) {
            assertEquals(VALID_GAME_ID, review.getId());
        }
    }

    @Test
    @Order(5)
    public void testGetReviewsByCustomerId() {
        String url = "/reviews/customer/" + VALID_CUSTOMER_ID;

        ResponseEntity<ReviewResponseDto[]> response = client.getForEntity(url, ReviewResponseDto[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto[] reviews = response.getBody();
        assertNotNull(reviews);
        assertTrue(reviews.length > 0);

        for (ReviewResponseDto review : reviews) {
            assertEquals(VALID_CUSTOMER_ID, review.getId());
        }
    }

    @Test
    @Order(6)
    public void testDeleteReview() {
        String url = "/reviews/" + this.validReviewId;

        client.delete(url);

        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
