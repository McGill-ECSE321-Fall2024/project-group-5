package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Game;


public class GameResponseDto {

    public enum CategoryResDto {
        Horror, Puzzle, Action, ActionAdventure, Sports, Strategy, RPG, Multiplayer, Simulation, Survival, Party, Shooter,
        Casual, Platformer, BattleRoyale, Sandbox, MMO
    }
    
    public enum GameConsoleResDto {
        PS4, PS5, WiiU, NintendoSwitch, PC, XBoxSeriesS, XBoxSeriesX, Mac
    }

    // Method to convert Category from Game to CategoryResDto
    public static CategoryResDto toCategoryResDto(Game.Category category) {
        if (category == null) {
            return null;
        }
        return CategoryResDto.valueOf(category.name());
    }

    // Method to convert GameConsole from Game to GameConsoleResDto
    public static GameConsoleResDto toGameConsoleResDto(Game.GameConsole gameConsole) {
        if (gameConsole == null) {
            return null;
        }
        return GameConsoleResDto.valueOf(gameConsole.name());
    }

    private int id;
    private String name;
    private int price;
    private String description;
    private CategoryResDto category;
    private GameConsoleResDto gameConsole;
    private boolean inCatalog;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GameResponseDto() {
    }

    public GameResponseDto(Game model) {
        this.id = model.getId();
        this.name = model.getName();
        this.price = model.getPrice();
        this.description = model.getDescription();
        this.category = toCategoryResDto(model.getCategory());
        this.gameConsole = toGameConsoleResDto(model.getGameConsole());
        this.inCatalog = model.getInCatalog();
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public CategoryResDto getCategory() {
        return category;
    }

    public GameConsoleResDto getGameConsole() {
        return gameConsole;
    }

    public boolean isInCatalog() {
        return inCatalog;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(CategoryResDto category) {
        this.category = category;
    }

    public void setGameConsole(GameConsoleResDto gameConsole) {
        this.gameConsole = gameConsole;
    }

    public void setInCatalog(boolean inCatalog) {
        this.inCatalog = inCatalog;
    }

}
