package ca.mcgill.ecse321.gamestore.model;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

// line 54 "model.ump"
// line 184 "model.ump"
@Entity
public class Catalog {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Catalog Attributes
  @Id
  @GeneratedValue
  private int id;

  // Catalog Associations
  @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL)
  private List<Game> games;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Catalog() {
    games = new ArrayList<Game>();
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public int getId() {
    return id;
  }

  /* Code from template association_GetMany */
  public Game getGame(int index) {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames() {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames() {
    int number = games.size();
    return number;
  }

  public boolean hasGames() {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame) {
    int index = games.indexOf(aGame);
    return index;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames() {
    return 0;
  }

  public boolean addGame(Game aGame) {
    boolean wasAdded = false;
    if (games.contains(aGame)) {
      return false;
    }
    Catalog existingCatalog = aGame.getCatalog();
    boolean isNewCatalog = existingCatalog != null && !this.equals(existingCatalog);
    if (isNewCatalog) {
      aGame.setCatalog(this);
    } else {
      games.add(aGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame) {
    boolean wasRemoved = false;
    // Unable to remove aGame, as it must always have a catalog
    if (!this.equals(aGame.getCatalog())) {
      games.remove(aGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index) {
    boolean wasAdded = false;
    if (addGame(aGame)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfGames()) {
        index = numberOfGames() - 1;
      }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index) {
    boolean wasAdded = false;
    if (games.contains(aGame)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfGames()) {
        index = numberOfGames() - 1;
      }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } else {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }

  public void delete() {
    for (int i = games.size(); i > 0; i--) {
      Game aGame = games.get(i - 1);
      aGame.delete();
    }
  }

  public String toString() {
    return super.toString() + "[" +
        "id" + ":" + getId() + "]";
  }
}