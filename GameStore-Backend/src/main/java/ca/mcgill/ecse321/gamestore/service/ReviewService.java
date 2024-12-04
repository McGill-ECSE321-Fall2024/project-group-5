package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.dao.CustomerAccountRepository;
import ca.mcgill.ecse321.gamestore.dao.GameRepository;
import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.Game;
import ca.mcgill.ecse321.gamestore.model.Review;
import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public Review getReviewById(int id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + id));
    }

    @Transactional
    public List<Review> getReviewsByGameId(int gameId) {
        return reviewRepository.findByGame_Id(gameId);
    }

    @Transactional
    public List<Review> getReviewsByCustomerAccountId(int customerAccountId) {
        return reviewRepository.findByCustomerAccount_Id(customerAccountId);
    }

    @Transactional
    public Review createReview(Date date, String description, int likeCount, int dislikeCount, float rating,
            boolean employeeReviewed, int customerAccountId, int gameId) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        // Retrieve CustomerAccount and Game with proper checks
        CustomerAccount customerAccount = customerAccountRepository.findById(customerAccountId)
                .orElseThrow(
                        () -> new IllegalArgumentException("CustomerAccount not found with ID: " + customerAccountId));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + gameId));

        Review review = new Review(date, description, likeCount, dislikeCount, rating, employeeReviewed,
                customerAccount, game);
        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(int reviewId, int likeCount, int dislikeCount) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + reviewId));

        if (likeCount < 0 || dislikeCount < 0) {
            throw new IllegalArgumentException("Like and dislike counts cannot be negative");
        }

        review.setLikeCount(likeCount);
        review.setDislikeCount(dislikeCount);
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(int reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + reviewId));
        reviewRepository.delete(review);
    }
}
