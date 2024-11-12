package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.CustomerAccount;

public class CustomerAccountResponseDto {
    private int id;
    private String username;
    private String password;
    private String emailAddress;
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

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getName() {
        return name;
    }

    public boolean setUsername(String aUsername) {
        boolean wasSet = false;
        if (aUsername == null) {
            return wasSet;
        }
        this.username = aUsername;
        wasSet = true;
        return wasSet;
    }

    public boolean setPassword(String aPassword) {
        boolean wasSet = false;
        if (aPassword == null) {
            return wasSet;
        }
        this.password = aPassword;
        wasSet = true;
        return wasSet;
    }

    public boolean setEmailAddress(String aEmailAddress) {
        boolean wasSet = false;
        if (aEmailAddress == null) {
            return wasSet;
        }
        this.emailAddress = aEmailAddress;
        wasSet = true;
        return wasSet;
    }

    public boolean setName(String aName) {
        boolean wasSet = false;
        if (aName == null) {
            return wasSet;
        }
        this.emailAddress = aName;
        wasSet = true;
        return wasSet;
    }
}