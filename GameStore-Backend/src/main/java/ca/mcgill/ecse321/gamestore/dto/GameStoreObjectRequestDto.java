package ca.mcgill.ecse321.gamestore.dto;

public class GameStoreObjectRequestDto {
    private String policy;

    // Default constructor for Jackson
    public GameStoreObjectRequestDto() {}

    public GameStoreObjectRequestDto(String policy) {
        this.policy = policy;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
}
