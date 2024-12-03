package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;

public class GameStoreObjectResponseDto {
    private int id;
    private String policy;

    // Default constructor for Jackson
    public GameStoreObjectResponseDto() {}

    public GameStoreObjectResponseDto(GameStoreObject model) {
        this.id = model.getId();
        this.policy = model.getPolicy();
    }

    public int getId() {
        return id;
    }

    public String getPolicy() {
        return policy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
