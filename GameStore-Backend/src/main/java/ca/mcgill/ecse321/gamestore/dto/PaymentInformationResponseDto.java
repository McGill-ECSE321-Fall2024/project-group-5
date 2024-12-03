// src/main/java/ca/mcgill/ecse321/gamestore/dto/PaymentInformationResponseDTO.java
package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;
import java.sql.Date;

public class PaymentInformationResponseDto {
    private int id;
    private String cardholderName;
    private int cardNumber;
    private CardType cardType;
    private Date expirationDate;
    private int customerAccountId;
    private int cvc; // Add the missing field for CVC

    // Private default constructor for Jackson
    @SuppressWarnings("unused")
    private PaymentInformationResponseDto() {}

    // Constructor from model
    public PaymentInformationResponseDto(PaymentInformation paymentInformation) {
        this.id = paymentInformation.getId();
        this.cardholderName = paymentInformation.getCardholderName();
        this.cardNumber= paymentInformation.getCardNumber();
        this.cardType = paymentInformation.getCardType();
        this.expirationDate = paymentInformation.getExpirationDate();
        if (paymentInformation.getCustomerAccount() == null) {
            throw new IllegalArgumentException("PaymentInformation object has no associated customer account.");
        }
        this.customerAccountId = paymentInformation.getCustomerAccount().getId();
        this.cvc = paymentInformation.getCvc(); // Initialize CVC from the model
    }

    public boolean setId(int id) {
        this.id = id;
        return true;
    }

    public boolean setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
        return true;
    }

    public boolean setCardType(CardType cardType) {
        this.cardType = cardType;
        return true;
    }

    public boolean setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return true;
    }

    public boolean setCustomerAccountId(int customerAccountId) {
        this.customerAccountId = customerAccountId;
        return true;
    }

    public boolean setCvc(int cvc) { // Add setter for CVC if necessary
        this.cvc = cvc;
        return true;
    }

    public boolean setCardNumber(int cardNumber){
        this.cardNumber = cardNumber;
        return true;
    }

    public int getId() { return id; }
    public String getCardholderName() { return cardholderName; }
    public CardType getCardType() { return cardType; }
    public Date getExpirationDate() { return expirationDate; }
    public int getCustomerAccountId() { return customerAccountId; }
    public int getCvc() { return cvc; } // Add getter for CVC
    public int getCardNumber() {return cardNumber;}
}
