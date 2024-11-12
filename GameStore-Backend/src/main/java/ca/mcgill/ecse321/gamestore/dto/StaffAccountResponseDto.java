package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;

public class StaffAccountResponseDto {
    private int id;
    private String name;
    private String username;

    public StaffAccountResponseDto(StaffAccount model) {
        this.id = model.getId();
        this.name = model.getName();
        this.username = model.getUsername();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
