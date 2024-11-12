package ca.mcgill.ecse321.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.dao.ReviewRepository;
import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.model.Review;
import jakarta.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional
    public Review createReview(ReviewRequestDto requestDto) {
        Review review = new Review();
        review.setName(requestDto.getName());
        review.setDate(Date.valueOf(LocalDate.now())); // Set current date
        return reviewRepository.save(review);
    }

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
    public List<Review> getReviewsByCustomerId(int customerId) {
        return reviewRepository.findByCustomerAccount_Id(customerId);
    }

    @Transactional
    public void deleteReview(int id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("Review not found");
        }
        reviewRepository.deleteById(id);
    }
}
