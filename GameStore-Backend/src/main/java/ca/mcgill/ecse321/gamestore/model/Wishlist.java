/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 49 "model.ump"
// line 179 "model.ump"
public class Wishlist
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wishlist Attributes
  private int id;

  //Wishlist Associations
  private CustomerAccount customerAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wishlist(int aId, CustomerAccount aCustomerAccount)
  {
    id = aId;
    if (aCustomerAccount == null || aCustomerAccount.getWishlist() != null)
    {
      throw new RuntimeException("Unable to create Wishlist due to aCustomerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customerAccount = aCustomerAccount;
  }

  public Wishlist(int aId, String aUsernameForCustomerAccount, String aPasswordHashForCustomerAccount, String aRandomPasswordForCustomerAccount, int aIdForCustomerAccount, String aEmailAddressForCustomerAccount)
  {
    id = aId;
    customerAccount = new CustomerAccount(aUsernameForCustomerAccount, aPasswordHashForCustomerAccount, aRandomPasswordForCustomerAccount, aIdForCustomerAccount, aEmailAddressForCustomerAccount, this);
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

  public void delete()
  {
    CustomerAccount existingCustomerAccount = customerAccount;
    customerAccount = null;
    if (existingCustomerAccount != null)
    {
      existingCustomerAccount.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null");
  }
}