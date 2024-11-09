package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// line 61 "model.ump"
// line 190 "model.ump"
@Entity
public class Address {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Address Attributes
  private String address;
  private String city;
  private String province;
  private String country;
  private String postalCode;
  @Id
  @GeneratedValue
  private int id;

  // Address Associations
  @ManyToOne
  @JoinColumn(name = "customer account id")
  private CustomerAccount customerAccount;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Address() {
  }

  public Address(String aAddress, String aCity, String aProvince, String aCountry, String aPostalCode,
      CustomerAccount aCustomerAccount) {
    address = aAddress;
    city = aCity;
    province = aProvince;
    country = aCountry;
    postalCode = aPostalCode;
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount) {
      throw new RuntimeException(
          "Unable to create address due to customerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setAddress(String aAddress) {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setCity(String aCity) {
    boolean wasSet = false;
    city = aCity;
    wasSet = true;
    return wasSet;
  }

  public boolean setProvince(String aProvince) {
    boolean wasSet = false;
    province = aProvince;
    wasSet = true;
    return wasSet;
  }

  public boolean setCountry(String aCountry) {
    boolean wasSet = false;
    country = aCountry;
    wasSet = true;
    return wasSet;
  }

  public boolean setPostalCode(String aPostalCode) {
    boolean wasSet = false;
    postalCode = aPostalCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public String getProvince() {
    return province;
  }

  public String getCountry() {
    return country;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public int getId() {
    return id;
  }

  /* Code from template association_GetMany */

  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount() {
    return customerAccount;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTransactions() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Transaction addTransaction(double aTotalPrice, boolean aIsPaid, boolean aDeliveryStatus, String aPromotionCode,
      boolean aUserAgreementCheck, PaymentInformation aPaymentInformation, CustomerAccount aCustomerAccount) {
    return new Transaction(aTotalPrice, aIsPaid, aDeliveryStatus, aUserAgreementCheck,
        aPaymentInformation,
        aCustomerAccount, this);
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

  public void delete() {
    this.customerAccount = null;
  }

  public String toString() {
    return super.toString() + "[" +
        "address" + ":" + getAddress() + "," +
        "city" + ":" + getCity() + "," +
        "province" + ":" + getProvince() + "," +
        "country" + ":" + getCountry() + "," +
        "postalCode" + ":" + getPostalCode() + "," +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "customerAccount = "
        + (getCustomerAccount() != null ? Integer.toHexString(System.identityHashCode(getCustomerAccount())) : "null");
  }
}