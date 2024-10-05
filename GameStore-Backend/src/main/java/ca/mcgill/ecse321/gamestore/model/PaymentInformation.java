/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 75 "model.ump"
// line 197 "model.ump"
public class PaymentInformation
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum CardType { Visa, Mastercard, Interact, AmericanExpress }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PaymentInformation Attributes
  private String cardholderName;
  private int cardNumber;
  private Date expirationDate;
  private int cvc;
  private CardType cardType;
  private int id;

  //PaymentInformation Associations
  private CustomerAccount customerAccount;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PaymentInformation(String aCardholderName, int aCardNumber, Date aExpirationDate, int aCvc, CardType aCardType, int aId, CustomerAccount aCustomerAccount)
  {
    cardholderName = aCardholderName;
    cardNumber = aCardNumber;
    expirationDate = aExpirationDate;
    cvc = aCvc;
    cardType = aCardType;
    id = aId;
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount)
    {
      throw new RuntimeException("Unable to create paymentInformation due to customerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orders = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCardholderName(String aCardholderName)
  {
    boolean wasSet = false;
    cardholderName = aCardholderName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCardNumber(int aCardNumber)
  {
    boolean wasSet = false;
    cardNumber = aCardNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationDate(Date aExpirationDate)
  {
    boolean wasSet = false;
    expirationDate = aExpirationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCvc(int aCvc)
  {
    boolean wasSet = false;
    cvc = aCvc;
    wasSet = true;
    return wasSet;
  }

  public boolean setCardType(CardType aCardType)
  {
    boolean wasSet = false;
    cardType = aCardType;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getCardholderName()
  {
    return cardholderName;
  }

  public int getCardNumber()
  {
    return cardNumber;
  }

  public Date getExpirationDate()
  {
    return expirationDate;
  }

  public int getCvc()
  {
    return cvc;
  }

  public CardType getCardType()
  {
    return cardType;
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
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
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
      existingCustomerAccount.removePaymentInformation(this);
    }
    customerAccount.addPaymentInformation(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(int aOrderId, double aTotalPrice, boolean aIsPaid, boolean aDeliveryStatus, String aPromotionCode, boolean aUserAgreementCheck, CustomerAccount aCustomerAccount, Cart aCart, Address aAddress)
  {
    return new Order(aOrderId, aTotalPrice, aIsPaid, aDeliveryStatus, aPromotionCode, aUserAgreementCheck, this, aCustomerAccount, aCart, aAddress);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    PaymentInformation existingPaymentInformation = aOrder.getPaymentInformation();
    boolean isNewPaymentInformation = existingPaymentInformation != null && !this.equals(existingPaymentInformation);
    if (isNewPaymentInformation)
    {
      aOrder.setPaymentInformation(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a paymentInformation
    if (!this.equals(aOrder.getPaymentInformation()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    CustomerAccount placeholderCustomerAccount = customerAccount;
    this.customerAccount = null;
    if(placeholderCustomerAccount != null)
    {
      placeholderCustomerAccount.removePaymentInformation(this);
    }
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "cardholderName" + ":" + getCardholderName()+ "," +
            "cardNumber" + ":" + getCardNumber()+ "," +
            "cvc" + ":" + getCvc()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "expirationDate" + "=" + (getExpirationDate() != null ? !getExpirationDate().equals(this)  ? getExpirationDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "cardType" + "=" + (getCardType() != null ? !getCardType().equals(this)  ? getCardType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null");
  }
}