package ca.mcgill.ecse321.gamestore.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;

public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CustomerAccountRepository customerAccountRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateReview() {
        // Arrange
        CustomerAccount customer = mock(CustomerAccount.class);
        Game game = mock(Game.class);
        var savedReview = new Review();

        // Mock the IDs for the customer and game
        when(customer.getId()).thenReturn(1);
        when(game.getId()).thenReturn(1);

        // Mock the saved review
        savedReview.setLikeCount(10);
        savedReview.setDislikeCount(3);
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // Act
        Review result = reviewService.createReview(
                Date.valueOf("2024-01-01"),
                "Amazing game!",
                10,
                3,
                4.5f,
                false,
                1,
                1);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getLikeCount());
        assertEquals(3, result.getDislikeCount());
        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(customerAccountRepository, times(1)).findById(1);
        verify(gameRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateReview() {
        // Arrange
        Review review = new Review();
        review.setLikeCount(10); // Existing like count
        review.setDislikeCount(3); // Existing dislike count

        // Mock the behavior of getId()
        when(review.getId()).thenReturn(1);

        // Mock the repository call
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        // Act
        Review result = reviewService.updateReview(1, 20, 5); // Corrected the method call syntax

        // Assert
        assertNotNull(result);
        assertEquals(20, result.getLikeCount());
        assertEquals(5, result.getDislikeCount());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testUpdateReviewNotFound() {
        // Arrange
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(1, 20, 5);
        });

        assertEquals("Review not found", exception.getMessage());
        verify(reviewRepository, never()).save(any(Review.class));
    }

    @Test
    public void testDeleteReview() {
        // Arrange
        when(reviewRepository.findById(1)).thenReturn(Optional.of(new Review()));

        // Act
        reviewService.deleteReview(1);

        // Assert
        verify(reviewRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteReviewNotFound() {
        // Arrange
        when(reviewRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.deleteReview(1);
        });

        assertEquals("Review not found", exception.getMessage());
        verify(reviewRepository, never()).deleteById(anyInt());
    }

    @Test
    public void testGetReviewsByGameId() {
        // Arrange
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review();
        Review review2 = new Review();

        // Mock the IDs
        when(review1.getId()).thenReturn(1);
        when(review2.getId()).thenReturn(2);

        reviews.add(review1);
        reviews.add(review2);

        when(reviewRepository.findByGame_Id(1)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getReviewsByGameId(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByGame_Id(1);
    }

    @Test
    public void testGetReviewsByCustomerAccountId() {
        // Arrange
        List<Review> reviews = new ArrayList<>();
        Review review1 = new Review();
        Review review2 = new Review();

        // Mock the behavior of getId()
        when(review1.getId()).thenReturn(1);
        when(review2.getId()).thenReturn(2);

        reviews.add(review1);
        reviews.add(review2);

        when(reviewRepository.findByCustomerAccount_Id(1)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.getReviewsByCustomerAccountId(1);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reviewRepository, times(1)).findByCustomerAccount_Id(1);
    }

}
