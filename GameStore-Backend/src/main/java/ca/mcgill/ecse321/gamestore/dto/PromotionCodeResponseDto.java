package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;

public class PromotionCodeResponseDto {
    private int id;
    private String name;
    private double discountValue;
    private String expirationDate; // Adjust type if needed, e.g., Date or LocalDate

    // Default constructor for Jackson
    public PromotionCodeResponseDto() {
    }

    // Constructor to create DTO from model
    public PromotionCodeResponseDto(PromotionCode model) {
        this.id = model.getId();
        this.name = model.getName();
        this.discountValue = model.getDiscountValue();
        this.expirationDate = model.getExpirationDate().toString(); // Adjust as per your date handling
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
