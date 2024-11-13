package ca.mcgill.ecse321.gamestore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Optional;

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
    public void testCreateReviewWithValidData() {
        CustomerAccount customer = new CustomerAccount();
        Game game = new Game();

        when(customerAccountRepository.findById(1)).thenReturn(Optional.of(customer));
        when(gameRepository.findById(1)).thenReturn(Optional.of(game));

        Review savedReview = new Review();
        savedReview.setId(1);

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        Review result = reviewService.createReview(
                Date.valueOf("2024-01-01"), "Great game!", 10, 5, 4.5f, false, 1, 1
        );

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void testUpdateReview() {
        Review review = new Review();
        review.setId(1);
        review.setLikeCount(5);
        review.setDislikeCount(3);

        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        Review result = reviewService.updateReview(1, 20, 10);

        assertNotNull(result);
        assertEquals(20, result.getLikeCount());
        assertEquals(10, result.getDislikeCount());
        verify(reviewRepository, times(1)).save(review);
    }

    @Test
    public void testDeleteReview() {
        when(reviewRepository.existsById(1)).thenReturn(true);

        reviewService.deleteReview(1);

        verify(reviewRepository, times(1)).deleteById(1);
    }
}
