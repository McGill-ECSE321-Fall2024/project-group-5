package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/reviews")
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto requestDto) {
        Review review = reviewService.createReview(requestDto);
        return new ReviewResponseDto(review);
    }

    @GetMapping("/reviews/{id}")
    public ReviewResponseDto getReviewById(@PathVariable int id) {
        Review review = reviewService.getReviewById(id);
        return new ReviewResponseDto(review);
    }

    @GetMapping("/reviews/game/{gameId}")
    public List<ReviewResponseDto> getReviewsByGameId(@PathVariable int gameId) {
        List<Review> reviews = reviewService.getReviewsByGameId(gameId);
        List<ReviewResponseDto> responseDtos = new ArrayList<>();
        for (Review review : reviews) {
            responseDtos.add(new ReviewResponseDto(review));
        }
        return responseDtos;
    }

    @GetMapping("/reviews/customer/{customerId}")
    public List<ReviewResponseDto> getReviewsByCustomerId(@PathVariable int customerId) {
        List<Review> reviews = reviewService.getReviewsByCustomerId(customerId);
        List<ReviewResponseDto> responseDtos = new ArrayList<>();
        for (Review review : reviews) {
            responseDtos.add(new ReviewResponseDto(review));
        }
        return responseDtos;
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}
