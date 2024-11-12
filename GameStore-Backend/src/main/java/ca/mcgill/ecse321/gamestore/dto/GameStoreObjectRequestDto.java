package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.GameStoreObject;

public class GameStoreObjectRequestDto {
    private int id;
    private String name;
    private String description;
    private double price;

    // Default constructor for Jackson
    @SuppressWarnings("unused")
    private GameStoreObjectRequestDto() {
    }

    public GameStoreObjectRequestDto(GameStoreObject model) {
        this.id = model.getId();
        this.name = model.getName();
        this.description = model.getDescription();
        this.price = model.getPrice();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
