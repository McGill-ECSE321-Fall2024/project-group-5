package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class GameRequestDto {
    private int gameID;
    private String name;
    private int price;
    private String description;
    private Category category; 
    private GameConsole gameConsole; 
    private boolean inCatalog;

    private GameQtyRequestDto gameQty;
    private List<ReviewResponseDto> reviewList;

    // Jackson needs a default constructor, but it doesn't need to be public
    @SuppressWarnings("unused")
    private GameRequestDto() {
    }

    public GameRequestDto(int gameID, String aName, int aPrice, String aDescription, Category aCategory, GameConsole aGameConsole, boolean isInCatalog, GameQtyRequestDto aGameQty, List<ReviewResponseDto> aReviewList) {
        this.gameID = gameID;
        this.name = aName;
        this.price = aPrice;
        this.description = aDescription;
        this.category = aCategory; //check enum
        this.gameConsole = aGameConsole; //check enum
        this.inCatalog = isInCatalog;
        setGameQty(aGameQty);
        setReviewList(aReviewList);
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

    public Category getCategory() {
        return category;
    }

    public GameConsole getGameConsole() {
        return gameConsole;
    }

    public boolean isInCatalog() {
        return inCatalog;
    }

    public GameQtyRequestDto getGameQty() {
        return gameQty;
    }

    public List<ReviewResponseDto> getReviewList() {
        return reviewList;
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setGameConsole(GameConsole gameConsole) {
        this.gameConsole = gameConsole;
    }

    public void setInCatalog(boolean inCatalog) {
        this.inCatalog = inCatalog;
    }

    public boolean setGameQty(GameQtyRequestDto aGameQty) {
        if (aGameQty == null) {
            return false;
        }
        this.gameQty = aGameQty;
        return true;
    }

    public boolean setReviewList(List<ReviewResponseDto> aReviewList) {
        if (aReviewList == null) {
            return false;
        }
        this.reviewList = aReviewList;
        return true;
    }
}
