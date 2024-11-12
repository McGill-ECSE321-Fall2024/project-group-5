package ca.mcgill.ecse321.gamestore.dto;

import java.sql.Date;

import ca.mcgill.ecse321.gamestore.model.PromotionCode;

public class PromotionCodeResponseDto {
    private int id;
    private String code;
    private int discountValue;
    private Date expirationDate;

    // Default constructor for Jackson
    public PromotionCodeResponseDto() {
    }

    // Constructor to create DTO from model
    public PromotionCodeResponseDto(PromotionCode model) {
        this.id = model.getId();
        this.code = model.getCode();
        this.discountValue = model.getPercentageValue();
        this.expirationDate = model.getExpirationDate();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return code;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String code) {
        this.code = code;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
