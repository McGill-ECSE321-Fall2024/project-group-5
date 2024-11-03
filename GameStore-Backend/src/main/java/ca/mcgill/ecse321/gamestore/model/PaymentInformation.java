package ca.mcgill.ecse321.gamestore.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// line 75 "model.ump"
// line 197 "model.ump"
@Entity
public class PaymentInformation {

  // ------------------------
  // ENUMERATIONS
  // ------------------------

  public enum CardType {
    Visa, Mastercard, Interact, AmericanExpress
  }

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // PaymentInformation Attributes
  private String cardholderName;
  private int cardNumber;
  private Date expirationDate;
  private int cvc;
  private CardType cardType;
  @Id
  @GeneratedValue
  private int id;

  // PaymentInformation Associations
  @ManyToOne
  @JoinColumn(name = "customer account")
  private CustomerAccount customerAccount;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public PaymentInformation() {
  }

  public PaymentInformation(String aCardholderName, int aCardNumber, Date aExpirationDate, int aCvc, CardType aCardType,
      CustomerAccount aCustomerAccount) {
    cardholderName = aCardholderName;
    cardNumber = aCardNumber;
    expirationDate = aExpirationDate;
    cvc = aCvc;
    cardType = aCardType;
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount) {
      throw new RuntimeException(
          "Unable to create paymentInformation due to customerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setCardholderName(String aCardholderName) {
    boolean wasSet = false;
    cardholderName = aCardholderName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCardNumber(int aCardNumber) {
    boolean wasSet = false;
    cardNumber = aCardNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationDate(Date aExpirationDate) {
    boolean wasSet = false;
    expirationDate = aExpirationDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setCvc(int aCvc) {
    boolean wasSet = false;
    cvc = aCvc;
    wasSet = true;
    return wasSet;
  }

  public boolean setCardType(CardType aCardType) {
    boolean wasSet = false;
    cardType = aCardType;
    wasSet = true;
    return wasSet;
  }

  public String getCardholderName() {
    return cardholderName;
  }

  public int getCardNumber() {
    return cardNumber;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public int getCvc() {
    return cvc;
  }

  public CardType getCardType() {
    return cardType;
  }

  public int getId() {
    return id;
  }

  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount() {
    return customerAccount;
  }

  /* Code from template association_SetOneToMany */
  public boolean setCustomerAccount(CustomerAccount aCustomerAccount) {
    boolean wasSet = false;
    if (aCustomerAccount == null) {
      return wasSet;
    }
    customerAccount = aCustomerAccount;
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTransactions() {
    return 0;
  }

  public void delete() {
    this.customerAccount = null;
  }

  public String toString() {
    return super.toString() + "[" +
        "cardholderName" + ":" + getCardholderName() + "," +
        "cardNumber" + ":" + getCardNumber() + "," +
        "cvc" + ":" + getCvc() + "," +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "expirationDate" + "="
        + (getExpirationDate() != null
            ? !getExpirationDate().equals(this) ? getExpirationDate().toString().replaceAll("  ", "    ") : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "cardType" + "="
        + (getCardType() != null
            ? !getCardType().equals(this) ? getCardType().toString().replaceAll("  ", "    ") : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "customerAccount = "
        + (getCustomerAccount() != null ? Integer.toHexString(System.identityHashCode(getCustomerAccount())) : "null");
  }
}