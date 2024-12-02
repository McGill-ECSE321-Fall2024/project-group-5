package ca.mcgill.ecse321.gamestore.dto;

public class CustomerAccountRequestDto {
    private String username;
    private String password;
    private String emailAddress;
    private String name;

    //
    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private CustomerAccountRequestDto() {
    }

    public CustomerAccountRequestDto(String username, String password, String emailAddress, String name) {
        setUsername(username);
        setPassword(password);
        setEmailAddress(emailAddress);
        setName(name);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
        this.name = aName;
        wasSet = true;
        return wasSet;
    }
}