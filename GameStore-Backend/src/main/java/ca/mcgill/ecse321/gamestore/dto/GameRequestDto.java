package ca.mcgill.ecse321.gamestore.dto;

public class GameRequestDto {
    public enum CategoryReqDto {
        Horror, Puzzle, Action, ActionAdventure, Sports, Strategy, RPG, Multiplayer, Simulation, Survival, Party, Shooter,
        Casual, Platformer, BattleRoyale, Sandbox, MMO
    }

    public enum GameConsoleReqDto {
        PS4, PS5, WiiU, NintendoSwitch, PC, XBoxSeriesS, XBoxSeriesX, Mac
    }

    private String name;
    private int price;
    private String description;
    private CategoryReqDto category;
    private GameConsoleReqDto gameConsole;
    private boolean inCatalog;

    public GameRequestDto(String name, int price, String description, CategoryReqDto category, GameConsoleReqDto gameConsole, boolean inCatalog) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.gameConsole = gameConsole;
        this.inCatalog = inCatalog;
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
}





