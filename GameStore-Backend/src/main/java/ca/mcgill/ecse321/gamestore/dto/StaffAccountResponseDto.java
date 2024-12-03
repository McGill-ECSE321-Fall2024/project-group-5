package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.StaffAccount;

public class StaffAccountResponseDto {
    private int id;
    private String name;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private StaffAccountResponseDto() {
    }

    public StaffAccountResponseDto(StaffAccount model) {
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

    public Object getUsername() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}