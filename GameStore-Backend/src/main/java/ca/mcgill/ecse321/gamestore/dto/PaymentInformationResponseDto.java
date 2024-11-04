package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.PaymentInformation;

public class PaymentInformationResponseDto {
    private int id;
    private String name;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private PaymentInformationResponseDto() {
    }

    public PaymentInformationResponseDto(PaymentInformation model) {
        this.id = model.getId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}