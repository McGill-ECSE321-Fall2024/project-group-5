// src/main/java/ca/mcgill/ecse321/gamestore/dto/PaymentInformationRequestDTO.java
package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;

public class PaymentInformationRequestDto {
    private String cardholderName;
    private int cardNumber;
    private String expirationDate;
    private int cvc;
    private CardType cardType;
    private int customerAccountId;

    // Private default constructor for Jackson
    @SuppressWarnings("unused")
    private PaymentInformationRequestDto() {}

    // Parameterized constructor
    public PaymentInformationRequestDto(String cardholderName, int cardNumber, String expirationDate, int cvc, CardType cardType, int customerAccountId) {
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
        this.cardType = cardType;
        this.customerAccountId = customerAccountId;
    }

    public boolean setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
        return true;
    }

    public boolean setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
        return true;
    }

    public boolean setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return true;
    }

    public boolean setCvc(int cvc) {
        this.cvc = cvc;
        return true;
    }

    public boolean setCardType(CardType cardType) {
        this.cardType = cardType;
        return true;
    }

    public boolean setCustomerAccountId(int customerAccountId) {
        this.customerAccountId = customerAccountId;
        return true;
    }

    public String getCardholderName() { return cardholderName; }
    public int getCardNumber() { return cardNumber; }
    public String getExpirationDate() { return expirationDate; }
    public int getCvc() { return cvc; }
    public CardType getCardType() { return cardType; }
    public int getCustomerAccountId() { return customerAccountId; }
}
