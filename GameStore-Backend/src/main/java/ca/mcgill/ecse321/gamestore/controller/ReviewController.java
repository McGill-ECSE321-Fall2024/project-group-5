package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto requestDto) {
        Review review = reviewService.createReview(
                requestDto.getDate(),
                requestDto.getDescription(),
                requestDto.getLikeCount(),
                requestDto.getDislikeCount(),
                requestDto.getRating(),
                requestDto.isEmployeeReviewed(),
                requestDto.getCustomerAccountId(),
                requestDto.getGameId()
        );
        return new ReviewResponseDto(review);
    }

    @GetMapping("/{id}")
    public ReviewResponseDto getReviewById(@PathVariable int id) {
        return new ReviewResponseDto(reviewService.getReviewById(id));
    }

    @GetMapping("/game/{gameId}")
    public List<ReviewResponseDto> getReviewsByGameId(@PathVariable int gameId) {
        List<Review> reviews = reviewService.getReviewsByGameId(gameId);
        List<ReviewResponseDto> responseDtos = new ArrayList<>();
        for (Review review : reviews) {
            responseDtos.add(new ReviewResponseDto(review));
        }
        return responseDtos;
    }

    @GetMapping("/customer/{customerId}")
    public List<ReviewResponseDto> getReviewsByCustomerId(@PathVariable int customerId) {
        List<Review> reviews = reviewService.getReviewsByCustomerAccountId(customerId);
        List<ReviewResponseDto> responseDtos = new ArrayList<>();
        for (Review review : reviews) {
            responseDtos.add(new ReviewResponseDto(review));
        }
        return responseDtos;
    }

    @PutMapping("/{id}/update")
    public ReviewResponseDto updateReview(
            @PathVariable int id,
            @RequestBody ReviewRequestDto requestDto) {
        Review updatedReview = reviewService.updateReview(
                id,
                requestDto.getLikeCount(),
                requestDto.getDislikeCount()
        );
        return new ReviewResponseDto(updatedReview);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }
}
