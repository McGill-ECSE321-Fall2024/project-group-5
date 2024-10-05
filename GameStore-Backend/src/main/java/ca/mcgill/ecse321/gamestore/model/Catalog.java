/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 54 "model.ump"
// line 184 "model.ump"
public class Catalog
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Category { Horror, Puzzle, Action, ActionjAdventure, Sports, Strategy, RPG, Multiplayer, Simulation, Survival, Party, Shooter, Casual, Platformer, BattleRoyale, Sandbox, MMO }
  public enum GameConsole { PS4, PS5, WiiU, NintendoSwitch, PC, XBoxSeriesS, XBoxSeriesX, Mac }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Catalog theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Catalog Attributes
  private int id;

  //Catalog Associations
  private List<Game> games;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private Catalog()
  {
    games = new ArrayList<Game>();
  }

  public static Catalog getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new Catalog();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template association_GetMany */
  public Game getGame(int index)
  {
    Game aGame = games.get(index);
    return aGame;
  }

  public List<Game> getGames()
  {
    List<Game> newGames = Collections.unmodifiableList(games);
    return newGames;
  }

  public int numberOfGames()
  {
    int number = games.size();
    return number;
  }

  public boolean hasGames()
  {
    boolean has = games.size() > 0;
    return has;
  }

  public int indexOfGame(Game aGame)
  {
    int index = games.indexOf(aGame);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGames()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Game addGame(String aName, int aPrice, String aDescription, Category aCategory, GameConsole aGameConsole, int aId)
  {
    return new Game(aName, aPrice, aDescription, aCategory, aGameConsole, aId, this);
  }

  public boolean addGame(Game aGame)
  {
    boolean wasAdded = false;
    if (games.contains(aGame)) { return false; }
    Catalog existingCatalog = aGame.getCatalog();
    boolean isNewCatalog = existingCatalog != null && !this.equals(existingCatalog);
    if (isNewCatalog)
    {
      aGame.setCatalog(this);
    }
    else
    {
      games.add(aGame);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGame(Game aGame)
  {
    boolean wasRemoved = false;
    //Unable to remove aGame, as it must always have a catalog
    if (!this.equals(aGame.getCatalog()))
    {
      games.remove(aGame);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameAt(Game aGame, int index)
  {  
    boolean wasAdded = false;
    if(addGame(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameAt(Game aGame, int index)
  {
    boolean wasAdded = false;
    if(games.contains(aGame))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGames()) { index = numberOfGames() - 1; }
      games.remove(aGame);
      games.add(index, aGame);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameAt(aGame, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=games.size(); i > 0; i--)
    {
      Game aGame = games.get(i - 1);
      aGame.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]";
  }
}