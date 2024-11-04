package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.CustomerAccount;

public class CustomerAccountResponseDto {
    private int id;
    private String name;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private CustomerAccountResponseDto() {
    }

    public CustomerAccountResponseDto(CustomerAccount model) {
        this.id = model.getId();
        this.name = model.getName();
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