package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

// line 111 "model.ump"
// line 215 "model.ump"
@Entity
public class Game {

  // ------------------------
  // ENUMERATIONS
  // ------------------------

  public enum Category {
    Horror, Puzzle, Action, ActionAdventure, Sports, Strategy, RPG, Multiplayer, Simulation, Survival, Party, Shooter,
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
  private boolean inCatalog;
  @Id
  @GeneratedValue
  private int id;

  // Game Associations

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
    inCatalog = false;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }
  
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

  public boolean setInCatalog(boolean aInCatalog) {
    boolean wasSet = false;
    inCatalog = aInCatalog;
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

  public boolean getInCatalog() {
    return inCatalog;
  }

  public int getId() {
    return id;
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
            : "null");
  }
}