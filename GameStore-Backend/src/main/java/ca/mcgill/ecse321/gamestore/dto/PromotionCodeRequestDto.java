package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;

public class PromotionCodeRequestDto {
    private int id;
    private String name;
    private double discountValue;
    private String expirationDate; // Assuming it's stored as a string or update as per your model's date type

    // Default constructor for Jackson
    public PromotionCodeRequestDto() {
    }

    // Constructor to create DTO from model
    public PromotionCodeRequestDto(PromotionCode model) {
        this.id = model.getId();
        this.name = model.getName();
        this.discountValue = model.getDiscountValue();
        this.expirationDate = model.getExpirationDate().toString(); // Adjust if necessary
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
