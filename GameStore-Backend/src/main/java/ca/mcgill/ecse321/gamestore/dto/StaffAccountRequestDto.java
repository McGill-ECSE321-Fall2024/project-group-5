package ca.mcgill.ecse321.gamestore.dto;

public class StaffAccountRequestDto {
    private String username;
    private String password;

    @SuppressWarnings("unused")
    public StaffAccountRequestDto() {
    }

    public StaffAccountRequestDto(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    // Getters and Setters
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