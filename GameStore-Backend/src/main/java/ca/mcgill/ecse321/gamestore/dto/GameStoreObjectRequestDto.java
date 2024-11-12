package ca.mcgill.ecse321.gamestore.dto;

public class GameStoreObjectRequestDto {
    private String policy;

    // Default constructor for Jackson
    @SuppressWarnings("unused")
    private GameStoreObjectRequestDto() {
    }

    public GameStoreObjectRequestDto(String aPolicy) {
        policy = aPolicy;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String aPolicy) {
        policy = aPolicy;
    }
}
