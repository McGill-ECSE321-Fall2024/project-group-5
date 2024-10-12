package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

// line 49 "model.ump"
// line 179 "model.ump"
@Entity
public class Wishlist {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Wishlist Attributes
  @Id
  @GeneratedValue
  private int id;

  // Wishlist Associations
  @OneToOne
  @JoinColumn(name = "customer account id", referencedColumnName = "id")
  private CustomerAccount customerAccount;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Wishlist() {
  }

  public Wishlist(CustomerAccount aCustomerAccount) {
    if (aCustomerAccount == null || aCustomerAccount.getWishlist() != null) {
      throw new RuntimeException(
          "Unable to create Wishlist due to aCustomerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    customerAccount = aCustomerAccount;
  }

  public Wishlist(String aUsernameForCustomerAccount, String aPasswordHashForCustomerAccount,
      String aRandomPasswordForCustomerAccount, int aIdForCustomerAccount, String aEmailAddressForCustomerAccount) {
    customerAccount = new CustomerAccount(aUsernameForCustomerAccount, aPasswordHashForCustomerAccount,
        aRandomPasswordForCustomerAccount, aEmailAddressForCustomerAccount, this);
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public int getId() {
    return id;
  }

  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount() {
    return customerAccount;
  }

  public void delete() {
    CustomerAccount existingCustomerAccount = customerAccount;
    customerAccount = null;
    if (existingCustomerAccount != null) {
      existingCustomerAccount.delete();
    }
  }

  public String toString() {
    return super.toString() + "[" +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "customerAccount = "
        + (getCustomerAccount() != null ? Integer.toHexString(System.identityHashCode(getCustomerAccount())) : "null");
  }
}