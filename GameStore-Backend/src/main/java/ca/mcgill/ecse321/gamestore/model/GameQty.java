package ca.mcgill.ecse321.gamestore.model;

// line 131 "model.ump"
// line 227 "model.ump"
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GameQty {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // GameQty Attributes
  private int qty;
  @Id
  @GeneratedValue
  private int id;

  // GameQty Associations
  @ManyToOne
  @JoinColumn(name = "transaction id")
  private Transaction transaction;
  @ManyToOne
  @JoinColumn(name = "game id")
  private Game game;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public GameQty() {
  }

  public GameQty(int aQty, int aId, Transaction aTransaction, Game aGame) {
    qty = aQty;
    id = aId;
    boolean didAddTransaction = setTransaction(aTransaction);
    if (!didAddTransaction) {
      throw new RuntimeException(
          "Unable to create gameQty due to transaction. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame) {
      throw new RuntimeException(
          "Unable to create gameQty due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setQty(int aQty) {
    boolean wasSet = false;
    qty = aQty;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public int getQty() {
    return qty;
  }

  public int getId() {
    return id;
  }

  /* Code from template association_GetOne */
  public Transaction getTransaction() {
    return transaction;
  }

  /* Code from template association_GetOne */
  public Game getGame() {
    return game;
  }

  /* Code from template association_SetOneToMany */
  public boolean setTransaction(Transaction aTransaction) {
    boolean wasSet = false;
    if (aTransaction == null) {
      return wasSet;
    }

    transaction = aTransaction;
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame) {
    boolean wasSet = false;
    if (aGame == null) {
      return wasSet;
    }

    game = aGame;
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    this.transaction = null;
    this.game = null;
  }

  public String toString() {
    return super.toString() + "[" +
        "qty" + ":" + getQty() + "," +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "transaction = "
        + (getTransaction() != null ? Integer.toHexString(System.identityHashCode(getTransaction())) : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "game = " + (getGame() != null ? Integer.toHexString(System.identityHashCode(getGame())) : "null");
  }
}