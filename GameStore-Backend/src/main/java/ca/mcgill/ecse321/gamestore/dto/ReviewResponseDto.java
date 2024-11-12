package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Review;

import java.sql.Date;

public class ReviewResponseDto {
    private int id;
    private Date date;
    private String description;
    private int likeCount;
    private int dislikeCount;
    private float rating;
    private boolean employeeReviewed;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.date = review.getDate();
        this.description = review.getDescription();
        this.likeCount = review.getLikeCount();
        this.dislikeCount = review.getDislikeCount();
        this.rating = review.getRating();
        this.employeeReviewed = review.isEmployeeReviewed();
    }

    // Getters only
    public int getId() { return id; }
    public Date getDate() { return date; }
    public String getDescription() { return description; }
    public int getLikeCount() { return likeCount; }
    public int getDislikeCount() { return dislikeCount; }
    public float getRating() { return rating; }
    public boolean isEmployeeReviewed() { return employeeReviewed; }
}
