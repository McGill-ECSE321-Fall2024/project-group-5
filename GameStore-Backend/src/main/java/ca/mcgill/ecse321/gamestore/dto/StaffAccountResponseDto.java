package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;

public class StaffAccountResponseDto {
    private int id;
    private String username;
    private String name;

    public StaffAccountResponseDto(StaffAccount staffAccount) {
        this.id = staffAccount.getId();
        this.username = staffAccount.getUsername();
        this.name = staffAccount.getName();
    }

    // Getters only (response DTOs should be immutable)
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getName() { return name; }
}
