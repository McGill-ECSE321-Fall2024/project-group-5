package ca.mcgill.ecse321.gamestore.dto;

import java.util.Date;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;

public class PaymentInformationRequestDto {
    private String cardholderName;
    private long cardNumber; // Changed to long for larger card numbers
    private Date expirationDate; // Changed to java.util.Date
    private int cvc;
    private CardType cardType;
    private int customerAccountId;

    // Constructor
    public PaymentInformationRequestDto(String cardholderName, long cardNumber, Date expirationDate, int cvc, CardType cardType, int customerAccountId) {
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvc = cvc;
        this.cardType = cardType;
        this.customerAccountId = customerAccountId;
    }

    // Getters and Setters
    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(int customerAccountId) {
        this.customerAccountId = customerAccountId;
    }
}
