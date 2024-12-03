package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Address;

public class AddressResponseDto {
    private int id;
    private String address;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    // Default constructor required for Jackson
    @SuppressWarnings("unused")
    private AddressResponseDto() {
    }

    // Constructor to populate fields from the Address model
    public AddressResponseDto(Address model) {
        this.id = model.getId();
        this.address = model.getAddress();
        this.city = model.getCity();
        this.province = model.getProvince();
        this.country = model.getCountry();
        this.postalCode = model.getPostalCode();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    // Setters (optional, typically not needed for response DTOs)
    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}
