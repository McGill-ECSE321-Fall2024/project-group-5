package ca.mcgill.ecse321.gamestore.model;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.sql.Date;

// line 111 "model.ump"
// line 215 "model.ump"
@Entity
public class Game {

  // ------------------------
  // ENUMERATIONS
  // ------------------------

  public enum Category {
    Horror, Puzzle, Action, ActionjAdventure, Sports, Strategy, RPG, Multiplayer, Simulation, Survival, Party, Shooter,
    Casual, Platformer, BattleRoyale, Sandbox, MMO
  }

  public enum GameConsole {
    PS4, PS5, WiiU, NintendoSwitch, PC, XBoxSeriesS, XBoxSeriesX, Mac
  }

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Game Attributes
  private String name;
  private int price;
  private String description;
  private Category category;
  private GameConsole gameConsole;
  @Id
  @GeneratedValue
  private int id;

  // Game Associations
  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
  private List<GameQty> gameQties = new ArrayList<>();
  @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
  private List<Review> reviews = new ArrayList<>();
  @ManyToOne
  @JoinColumn(name = "catalog id")
  private Catalog catalog;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Game() {
  }

  public Game(String aName, int aPrice, String aDescription, Category aCategory, GameConsole aGameConsole) {
    name = aName;
    price = aPrice;
    description = aDescription;
    category = aCategory;
    gameConsole = aGameConsole;
    gameQties = new ArrayList<GameQty>();
    reviews = new ArrayList<Review>();
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setName(String aName) {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice) {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription) {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setCategory(Category aCategory) {
    boolean wasSet = false;
    category = aCategory;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameConsole(GameConsole aGameConsole) {
    boolean wasSet = false;
    gameConsole = aGameConsole;
    wasSet = true;
    return wasSet;
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

  public int getId() {
    return id;
  }

  /* Code from template association_GetMany */
  public GameQty getGameQty(int index) {
    GameQty aGameQty = gameQties.get(index);
    return aGameQty;
  }

  public List<GameQty> getGameQties() {
    List<GameQty> newGameQties = Collections.unmodifiableList(gameQties);
    return newGameQties;
  }

  public int numberOfGameQties() {
    int number = gameQties.size();
    return number;
  }

  public boolean hasGameQties() {
    boolean has = gameQties.size() > 0;
    return has;
  }

  public int indexOfGameQty(GameQty aGameQty) {
    int index = gameQties.indexOf(aGameQty);
    return index;
  }

  /* Code from template association_GetMany */
  public Review getReview(int index) {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews() {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews() {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews() {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview) {
    int index = reviews.indexOf(aReview);
    return index;
  }

  /* Code from template association_GetOne */
  public Catalog getCatalog() {
    return catalog;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameQties() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public GameQty addGameQty(int aQty, int aId, Cart aCart) {
    return new GameQty(aQty, aId, aCart, this);
  }

  public boolean addGameQty(GameQty aGameQty) {
    boolean wasAdded = false;
    if (gameQties.contains(aGameQty)) {
      return false;
    }
    Game existingGame = aGameQty.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame) {
      aGameQty.setGame(this);
    } else {
      gameQties.add(aGameQty);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameQty(GameQty aGameQty) {
    boolean wasRemoved = false;
    // Unable to remove aGameQty, as it must always have a game
    if (!this.equals(aGameQty.getGame())) {
      gameQties.remove(aGameQty);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameQtyAt(GameQty aGameQty, int index) {
    boolean wasAdded = false;
    if (addGameQty(aGameQty)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfGameQties()) {
        index = numberOfGameQties() - 1;
      }
      gameQties.remove(aGameQty);
      gameQties.add(index, aGameQty);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameQtyAt(GameQty aGameQty, int index) {
    boolean wasAdded = false;
    if (gameQties.contains(aGameQty)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfGameQties()) {
        index = numberOfGameQties() - 1;
      }
      gameQties.remove(aGameQty);
      gameQties.add(index, aGameQty);
      wasAdded = true;
    } else {
      wasAdded = addGameQtyAt(aGameQty, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Review addReview(Date aDate, String aDescription, int aLikeCount, int aDislikeCount, float aRating,
      boolean aEmployeeReviewed, CustomerAccount aCustomerAccount) {
    return new Review(aDate, aDescription, aLikeCount, aDislikeCount, aRating, aEmployeeReviewed, aCustomerAccount,
        this);
  }

  public boolean addReview(Review aReview) {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) {
      return false;
    }
    Game existingGame = aReview.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame) {
      aReview.setGame(this);
    } else {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview) {
    boolean wasRemoved = false;
    // Unable to remove aReview, as it must always have a game
    if (!this.equals(aReview.getGame())) {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index) {
    boolean wasAdded = false;
    if (addReview(aReview)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfReviews()) {
        index = numberOfReviews() - 1;
      }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index) {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfReviews()) {
        index = numberOfReviews() - 1;
      }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } else {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }

  /* Code from template association_SetOneToMany */
  public boolean setCatalog(Catalog aCatalog) {
    boolean wasSet = false;
    if (aCatalog == null) {
      return wasSet;
    }

    Catalog existingCatalog = catalog;
    catalog = aCatalog;
    if (existingCatalog != null && !existingCatalog.equals(aCatalog)) {
      existingCatalog.removeGame(this);
    }
    catalog.addGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    for (int i = gameQties.size(); i > 0; i--) {
      GameQty aGameQty = gameQties.get(i - 1);
      aGameQty.delete();
    }
    for (int i = reviews.size(); i > 0; i--) {
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    Catalog placeholderCatalog = catalog;
    this.catalog = null;
    if (placeholderCatalog != null) {
      placeholderCatalog.removeGame(this);
    }
  }

  public String toString() {
    return super.toString() + "[" +
        "name" + ":" + getName() + "," +
        "price" + ":" + getPrice() + "," +
        "description" + ":" + getDescription() + "," +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "category" + "="
        + (getCategory() != null
            ? !getCategory().equals(this) ? getCategory().toString().replaceAll("  ", "    ") : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "gameConsole" + "="
        + (getGameConsole() != null
            ? !getGameConsole().equals(this) ? getGameConsole().toString().replaceAll("  ", "    ") : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "catalog = "
        + (getCatalog() != null ? Integer.toHexString(System.identityHashCode(getCatalog())) : "null");
  }
}