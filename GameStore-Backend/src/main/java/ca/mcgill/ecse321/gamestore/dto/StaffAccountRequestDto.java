package ca.mcgill.ecse321.gamestore.dto;

public class StaffAccountRequestDto {
    private String name;
    private String username;
    private String password; // Avoid exposing passwords unnecessarily

    public StaffAccountRequestDto() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
