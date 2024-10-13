package ca.mcgill.ecse321.gamestore.model;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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
  @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
  private List<Transaction> transactions;
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
    transactions = new ArrayList<Transaction>();
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
  public Transaction getTransaction(int index) {
    Transaction aTransaction = transactions.get(index);
    return aTransaction;
  }

  public List<Transaction> getTransactions() {
    List<Transaction> newTransactions = Collections.unmodifiableList(transactions);
    return newTransactions;
  }

  public int numberOfTransactions() {
    int number = transactions.size();
    return number;
  }

  public boolean hasTransactions() {
    boolean has = transactions.size() > 0;
    return has;
  }

  public int indexOfTransaction(Transaction aTransaction) {
    int index = transactions.indexOf(aTransaction);
    return index;
  }

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
      boolean aUserAgreementCheck, PaymentInformation aPaymentInformation, CustomerAccount aCustomerAccount,
      Cart aCart) {
    return new Transaction(aTotalPrice, aIsPaid, aDeliveryStatus, aPromotionCode, aUserAgreementCheck,
        aPaymentInformation,
        aCustomerAccount, aCart, this);
  }

  public boolean addTransaction(Transaction aTransaction) {
    boolean wasAdded = false;
    if (transactions.contains(aTransaction)) {
      return false;
    }
    Address existingAddress = aTransaction.getAddress();
    boolean isNewAddress = existingAddress != null && !this.equals(existingAddress);
    if (isNewAddress) {
      aTransaction.setAddress(this);
    } else {
      transactions.add(aTransaction);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTransaction(Transaction aTransaction) {
    boolean wasRemoved = false;
    // Unable to remove aTransaction, as it must always have a address
    if (!this.equals(aTransaction.getAddress())) {
      transactions.remove(aTransaction);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addTransactionAt(Transaction aTransaction, int index) {
    boolean wasAdded = false;
    if (addTransaction(aTransaction)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfTransactions()) {
        index = numberOfTransactions() - 1;
      }
      transactions.remove(aTransaction);
      transactions.add(index, aTransaction);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTransactionAt(Transaction aTransaction, int index) {
    boolean wasAdded = false;
    if (transactions.contains(aTransaction)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfTransactions()) {
        index = numberOfTransactions() - 1;
      }
      transactions.remove(aTransaction);
      transactions.add(index, aTransaction);
      wasAdded = true;
    } else {
      wasAdded = addTransactionAt(aTransaction, index);
    }
    return wasAdded;
  }

  /* Code from template association_SetOneToMany */
  public boolean setCustomerAccount(CustomerAccount aCustomerAccount) {
    boolean wasSet = false;
    if (aCustomerAccount == null) {
      return wasSet;
    }

    CustomerAccount existingCustomerAccount = customerAccount;
    customerAccount = aCustomerAccount;
    if (existingCustomerAccount != null && !existingCustomerAccount.equals(aCustomerAccount)) {
      existingCustomerAccount.removeAddress(this);
    }
    customerAccount.addAddress(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    for (int i = transactions.size(); i > 0; i--) {
      Transaction aTransaction = transactions.get(i - 1);
      aTransaction.delete();
    }
    CustomerAccount placeholderCustomerAccount = customerAccount;
    this.customerAccount = null;
    if (placeholderCustomerAccount != null) {
      placeholderCustomerAccount.removeAddress(this);
    }
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