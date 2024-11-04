package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Address;

public class AddressResponseDto {
    private int id;
    private String name;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private AddressResponseDto() {
    }

    public AddressResponseDto(Address model) {
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