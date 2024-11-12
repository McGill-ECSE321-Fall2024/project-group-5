package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.model.CustomerAccount;
import ca.mcgill.ecse321.gamestore.model.Game;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Review createReview(Date date, String description, int likeCount, int dislikeCount, float rating,
                               boolean employeeReviewed, int customerAccountId, int gameId) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        CustomerAccount customerAccount = customerAccountRepository.findById(customerAccountId)
                .orElseThrow(() -> new IllegalArgumentException("CustomerAccount not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Review review = new Review(date, description, likeCount, dislikeCount, rating, employeeReviewed, customerAccount, game);
        return reviewRepository.save(review);
    }
}
