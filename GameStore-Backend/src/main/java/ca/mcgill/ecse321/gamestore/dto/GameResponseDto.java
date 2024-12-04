package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Game;

public class GameResponseDto {
    private int id;
    private String name;
    private int price;
    private String description;
    private String category;
    private String gameConsole;
    private boolean inCatalog;

    // Required for JSON deserialization
    public GameResponseDto() {
    }

    public GameResponseDto(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.price = game.getPrice();
        this.description = game.getDescription();
        this.category = game.getCategory().name();
        this.gameConsole = game.getGameConsole().name();
        this.inCatalog = game.getInCatalog();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGameConsole() {
        return gameConsole;
    }

    public void setGameConsole(String gameConsole) {
        this.gameConsole = gameConsole;
    }

    public boolean isInCatalog() {
        return inCatalog;
    }

    public void setInCatalog(boolean inCatalog) {
        this.inCatalog = inCatalog;
    }
}
