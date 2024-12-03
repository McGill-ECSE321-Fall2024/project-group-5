package ca.mcgill.ecse321.gamestore.dto;

import java.sql.Date;

public class ReviewRequestDto {
    private Date date;
    private String description;
    private int likeCount;
    private int dislikeCount;
    private float rating;
    private boolean employeeReviewed;
    private int customerAccountId;
    private int gameId;

    // Getters and Setters
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getLikeCount() { return likeCount; }
    public void setLikeCount(int likeCount) { this.likeCount = likeCount; }
    public int getDislikeCount() { return dislikeCount; }
    public void setDislikeCount(int dislikeCount) { this.dislikeCount = dislikeCount; }
    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
    public boolean isEmployeeReviewed() { return employeeReviewed; }
    public void setEmployeeReviewed(boolean employeeReviewed) { this.employeeReviewed = employeeReviewed; }
    public int getCustomerAccountId() { return customerAccountId; }
    public void setCustomerAccountId(int customerAccountId) { this.customerAccountId = customerAccountId; }
    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }
}