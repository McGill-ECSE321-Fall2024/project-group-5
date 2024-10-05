/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 41 "model.ump"
// line 171 "model.ump"
public class Cart
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Cart Attributes
  private int id;

  //Cart Associations
  private CustomerAccount customerAccount;
  private List<GameQty> gameQties;
  private Order order;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Cart(int aId, CustomerAccount aCustomerAccount)
  {
    id = aId;
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount)
    {
      throw new RuntimeException("Unable to create cart due to customerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    gameQties = new ArrayList<GameQty>();
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
  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount()
  {
    return customerAccount;
  }
  /* Code from template association_GetMany */
  public GameQty getGameQty(int index)
  {
    GameQty aGameQty = gameQties.get(index);
    return aGameQty;
  }

  public List<GameQty> getGameQties()
  {
    List<GameQty> newGameQties = Collections.unmodifiableList(gameQties);
    return newGameQties;
  }

  public int numberOfGameQties()
  {
    int number = gameQties.size();
    return number;
  }

  public boolean hasGameQties()
  {
    boolean has = gameQties.size() > 0;
    return has;
  }

  public int indexOfGameQty(GameQty aGameQty)
  {
    int index = gameQties.indexOf(aGameQty);
    return index;
  }
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }

  public boolean hasOrder()
  {
    boolean has = order != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomerAccount(CustomerAccount aCustomerAccount)
  {
    boolean wasSet = false;
    if (aCustomerAccount == null)
    {
      return wasSet;
    }

    CustomerAccount existingCustomerAccount = customerAccount;
    customerAccount = aCustomerAccount;
    if (existingCustomerAccount != null && !existingCustomerAccount.equals(aCustomerAccount))
    {
      existingCustomerAccount.removeCart(this);
    }
    customerAccount.addCart(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameQties()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GameQty addGameQty(int aQty, int aId, Game aGame)
  {
    return new GameQty(aQty, aId, this, aGame);
  }

  public boolean addGameQty(GameQty aGameQty)
  {
    boolean wasAdded = false;
    if (gameQties.contains(aGameQty)) { return false; }
    Cart existingCart = aGameQty.getCart();
    boolean isNewCart = existingCart != null && !this.equals(existingCart);
    if (isNewCart)
    {
      aGameQty.setCart(this);
    }
    else
    {
      gameQties.add(aGameQty);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameQty(GameQty aGameQty)
  {
    boolean wasRemoved = false;
    //Unable to remove aGameQty, as it must always have a cart
    if (!this.equals(aGameQty.getCart()))
    {
      gameQties.remove(aGameQty);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameQtyAt(GameQty aGameQty, int index)
  {  
    boolean wasAdded = false;
    if(addGameQty(aGameQty))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameQties()) { index = numberOfGameQties() - 1; }
      gameQties.remove(aGameQty);
      gameQties.add(index, aGameQty);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameQtyAt(GameQty aGameQty, int index)
  {
    boolean wasAdded = false;
    if(gameQties.contains(aGameQty))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameQties()) { index = numberOfGameQties() - 1; }
      gameQties.remove(aGameQty);
      gameQties.add(index, aGameQty);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameQtyAt(aGameQty, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setOrder(Order aNewOrder)
  {
    boolean wasSet = false;
    if (order != null && !order.equals(aNewOrder) && equals(order.getCart()))
    {
      //Unable to setOrder, as existing order would become an orphan
      return wasSet;
    }

    order = aNewOrder;
    Cart anOldCart = aNewOrder != null ? aNewOrder.getCart() : null;

    if (!this.equals(anOldCart))
    {
      if (anOldCart != null)
      {
        anOldCart.order = null;
      }
      if (order != null)
      {
        order.setCart(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    CustomerAccount placeholderCustomerAccount = customerAccount;
    this.customerAccount = null;
    if(placeholderCustomerAccount != null)
    {
      placeholderCustomerAccount.removeCart(this);
    }
    for(int i=gameQties.size(); i > 0; i--)
    {
      GameQty aGameQty = gameQties.get(i - 1);
      aGameQty.delete();
    }
    Order existingOrder = order;
    order = null;
    if (existingOrder != null)
    {
      existingOrder.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }
}