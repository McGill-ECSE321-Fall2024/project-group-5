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
  @JoinColumn(name = "cart id")
  private Cart cart;
  @ManyToOne
  @JoinColumn(name = "game id")
  private Game game;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public GameQty() {
  }

  public GameQty(int aQty, int aId, Cart aCart, Game aGame) {
    qty = aQty;
    id = aId;
    boolean didAddCart = setCart(aCart);
    if (!didAddCart) {
      throw new RuntimeException(
          "Unable to create gameQty due to cart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Cart getCart() {
    return cart;
  }

  /* Code from template association_GetOne */
  public Game getGame() {
    return game;
  }

  /* Code from template association_SetOneToMany */
  public boolean setCart(Cart aCart) {
    boolean wasSet = false;
    if (aCart == null) {
      return wasSet;
    }

    Cart existingCart = cart;
    cart = aCart;
    if (existingCart != null && !existingCart.equals(aCart)) {
      existingCart.removeGameQty(this);
    }
    cart.addGameQty(this);
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame) {
    boolean wasSet = false;
    if (aGame == null) {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame)) {
      existingGame.removeGameQty(this);
    }
    game.addGameQty(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    Cart placeholderCart = cart;
    this.cart = null;
    if (placeholderCart != null) {
      placeholderCart.removeGameQty(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if (placeholderGame != null) {
      placeholderGame.removeGameQty(this);
    }
  }

  public String toString() {
    return super.toString() + "[" +
        "qty" + ":" + getQty() + "," +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "cart = " + (getCart() != null ? Integer.toHexString(System.identityHashCode(getCart())) : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "game = " + (getGame() != null ? Integer.toHexString(System.identityHashCode(getGame())) : "null");
  }
}