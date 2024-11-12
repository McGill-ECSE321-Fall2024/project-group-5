package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    /*
     * @Autowired
     * private ReviewService reviewService;
     * 
     * @PostMapping("/reviews")
     * public ReviewResponseDto createReview(@RequestBody ReviewRequestDto
     * requestDto) {
     * Review review = reviewService.createReview(
     * requestDto.getDate(),
     * requestDto.getDescription(),
     * requestDto.getLikeCount(),
     * requestDto.getDislikeCount(),
     * requestDto.getRating(),
     * requestDto.isEmployeeReviewed(),
     * requestDto.getCustomerAccountId(),
     * requestDto.getGameId());
     * return new ReviewResponseDto(review);
     * }
     */
}