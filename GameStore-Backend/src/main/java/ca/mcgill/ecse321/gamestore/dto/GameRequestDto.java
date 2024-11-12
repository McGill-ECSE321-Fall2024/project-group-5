package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class GameRequestDto {

    public enum CategoryReqDto {
        Horror, Puzzle, Action, ActionjAdventure, Sports, Strategy, RPG, Multiplayer, Simulation, Survival, Party, Shooter,
        Casual, Platformer, BattleRoyale, Sandbox, MMO
    }
    
    public enum GameConsoleReqDto {
        PS4, PS5, WiiU, NintendoSwitch, PC, XBoxSeriesS, XBoxSeriesX, Mac
    }

    private int gameID;
    private String name;
    private int price;
    private String description;
    private CategoryReqDto category; 
    private GameConsoleReqDto gameConsole; 
    private boolean inCatalog;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GameRequestDto() {
    }

    public GameRequestDto(int gameID, String aName, int aPrice, String aDescription, CategoryReqDto aCategory, GameConsoleReqDto aGameConsole, boolean isInCatalog, GameQtyRequestDto aGameQty, List<ReviewResponseDto> aReviewList) {
        this.gameID = gameID;
        this.name = aName;
        this.price = aPrice;
        this.description = aDescription;
        this.category = aCategory; //check enum
        this.gameConsole = aGameConsole; //check enum
        this.inCatalog = isInCatalog;
    }

    // Getters
    public int getGameID() {
        return gameID;
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

    public CategoryReqDto getCategory() {
        return category;
    }

    public GameConsoleReqDto getGameConsole() {
        return gameConsole;
    }

    public boolean isInCatalog() {
        return inCatalog;
    }

    // Setters
    public void setGameID(int gameID) {
        this.gameID = gameID;
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

    public void setCategory(CategoryReqDto category) {
        this.category = category;
    }

    public void setGameConsole(GameConsoleReqDto gameConsole) {
        this.gameConsole = gameConsole;
    }

    public void setInCatalog(boolean inCatalog) {
        this.inCatalog = inCatalog;
    }
}


