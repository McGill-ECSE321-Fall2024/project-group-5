package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Address;

public class AddressRequestDto {
    private int id;
    private String address;
    private String city;
    private String province;
    private String country;
    private String postalCode;
    private String customerName;

    // Default constructor
    public AddressRequestDto() {}

    // Constructor
    public AddressRequestDto(Address model) {
        this.id = model.getId();
        this.address = model.getAddress();
        this.city = model.getCity();
        this.province = model.getProvince();
        this.country = model.getCountry();
        this.postalCode = model.getPostalCode();
        this.customerName = model.getCustomerAccount() != null ? model.getCustomerAccount().getName() : null;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
}