package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.PaymentInformation;
import ca.mcgill.ecse321.gamestore.model.PaymentInformation.CardType;

import java.sql.Date;

public class PaymentInformationResponseDto {

    private String cardholderName;
    private long cardNumber;
    private Date expirationDate;
    private int cvc;
    private CardType cardType;
    private int customerAccountId;

    public PaymentInformationResponseDto(PaymentInformation paymentInformation) {
        this.cardholderName = paymentInformation.getCardholderName();
        this.cardNumber = paymentInformation.getCardNumber();
        this.expirationDate = paymentInformation.getExpirationDate();
        this.cvc = paymentInformation.getCvc();
        this.cardType = paymentInformation.getCardType();
        this.customerAccountId = paymentInformation.getCustomerAccount().getId();
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
