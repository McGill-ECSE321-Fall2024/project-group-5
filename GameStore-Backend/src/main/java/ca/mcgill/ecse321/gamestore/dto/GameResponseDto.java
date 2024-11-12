package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

import ca.mcgill.ecse321.gamestore.model.Game;


public class GameResponseDto {

    public enum CategoryResDto {
        Horror, Puzzle, Action, ActionjAdventure, Sports, Strategy, RPG, Multiplayer, Simulation, Survival, Party, Shooter,
        Casual, Platformer, BattleRoyale, Sandbox, MMO
    }
    
    public enum GameConsoleResDto {
        PS4, PS5, WiiU, NintendoSwitch, PC, XBoxSeriesS, XBoxSeriesX, Mac
    }

    // Method to convert Category from Game to CategoryResDto
    private static CategoryResDto toCategoryResDto(Game.Category category) {
        if (category == null) {
            return null;
        }
        return CategoryResDto.valueOf(category.name());
    }

    // Method to convert GameConsole from Game to GameConsoleResDto
    private static GameConsoleResDto toGameConsoleResDto(Game.GameConsole gameConsole) {
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

    private GameQtyResponseDto gameQty;
    private List<ReviewResponseDto> reviewList;

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

        // // Convert GameQty model to GameQtyResponseDto if present
        // if (model.getQty() != null) {
        //     setGameQty(new GameQtyResponseDto(model.getGameQty()));
        // }

        // // Convert each Review model to ReviewResponseDto if present
        // if (model.getReviewList() != null) {
        //     this.reviewList = model.getReviewList().stream()
        //                             .map(ReviewResponseDto::new)
        //                             .collect(Collectors.toList());
        // }
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

    public GameQtyResponseDto getGameQty() {
        return gameQty;
    }

    public List<ReviewResponseDto> getReviewList() {
        return reviewList;
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

    // Method to set GameQtyResponseDto
    public boolean setGameQty(GameQtyResponseDto gameQty) {
        boolean wasSet = false;
        if (gameQty == null) {
            return wasSet;
        }
        this.gameQty = gameQty;
        wasSet = true;
        return wasSet;
    }

    // Method to set ReviewResponseDto list
    public boolean setReviewList(List<ReviewResponseDto> reviewList) {
        boolean wasSet = false;
        if (reviewList == null) {
            return wasSet;
        }
        this.reviewList = reviewList;
        wasSet = true;
        return wasSet;
    }
}
