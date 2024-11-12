package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.Game;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

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
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
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

        CustomerAccount customerAccount = customerAccountRepository.findById(customerAccountId)
                .orElseThrow(() -> new IllegalArgumentException("CustomerAccount not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Review review = new Review(date, description, likeCount, dislikeCount, rating, employeeReviewed, customerAccount, game);
        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(int reviewId, int likeCount, int dislikeCount) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        if (likeCount < 0 || dislikeCount < 0) {
            throw new IllegalArgumentException("Like and dislike counts cannot be negative");
        }

        review.setLikeCount(likeCount);
        review.setDislikeCount(dislikeCount);
        return reviewRepository.save(review);
    }
}
