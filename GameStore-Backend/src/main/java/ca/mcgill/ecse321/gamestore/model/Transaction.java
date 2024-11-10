package ca.mcgill.ecse321.gamestore.model;

// line 85 "model.ump"
// line 202 "model.ump"
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Transaction {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Transaction Attributes
  @Id
  @GeneratedValue
  private int transactionId;
  private double totalPrice;
  private boolean isPaid;
  private boolean deliveryStatus;
  private boolean userAgreementCheck;

  // Transaction Associations
  @ManyToOne
  @JoinColumn(name = "payment information id")
  private PaymentInformation paymentInformation;
  @ManyToOne
  @JoinColumn(name = "customer account id")
  private CustomerAccount customerAccount;
  @ManyToOne
  @JoinColumn(name = "address id")
  private Address address;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Transaction() {
  }

  public Transaction(double aTotalPrice, boolean aIsPaid, boolean aDeliveryStatus,
      boolean aUserAgreementCheck, PaymentInformation aPaymentInformation, CustomerAccount aCustomerAccount,
      Address aAddress) {
    totalPrice = aTotalPrice;
    isPaid = aIsPaid;
    deliveryStatus = aDeliveryStatus;
    userAgreementCheck = aUserAgreementCheck;
    boolean didAddPaymentInformation = setPaymentInformation(aPaymentInformation);
    if (!didAddPaymentInformation) {
      throw new RuntimeException(
          "Unable to create transaction due to paymentInformation. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount) {
      throw new RuntimeException(
          "Unable to create transaction due to customerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAddress = setAddress(aAddress);
    if (!didAddAddress) {
      throw new RuntimeException(
          "Unable to create transaction due to address. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setTotalPrice(double aTotalPrice) {
    boolean wasSet = false;
    totalPrice = aTotalPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPaid(boolean aIsPaid) {
    boolean wasSet = false;
    isPaid = aIsPaid;
    wasSet = true;
    return wasSet;
  }

  public boolean setDeliveryStatus(boolean aDeliveryStatus) {
    boolean wasSet = false;
    deliveryStatus = aDeliveryStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserAgreementCheck(boolean aUserAgreementCheck) {
    boolean wasSet = false;
    userAgreementCheck = aUserAgreementCheck;
    wasSet = true;
    return wasSet;
  }

  public int getTransactionId() {
    return transactionId;
  }

  public double getTotalPrice() {
    return totalPrice;
  }

  public boolean getIsPaid() {
    return isPaid;
  }

  public boolean getDeliveryStatus() {
    return deliveryStatus;
  }

  public boolean getUserAgreementCheck() {
    return userAgreementCheck;
  }

  /* Code from template association_GetOne */
  public PaymentInformation getPaymentInformation() {
    return paymentInformation;
  }

  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount() {
    return customerAccount;
  }

  /* Code from template association_GetOne */
  public Address getAddress() {
    return address;
  }

  /* Code from template association_SetOneToMany */
  public boolean setPaymentInformation(PaymentInformation aPaymentInformation) {
    boolean wasSet = false;
    if (aPaymentInformation == null) {
      return wasSet;
    }

    paymentInformation = aPaymentInformation;
    wasSet = true;
    return wasSet;
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

  /* Code from template association_SetOneToMany */
  public boolean setAddress(Address aAddress) {
    boolean wasSet = false;
    if (aAddress == null) {
      return wasSet;
    }

    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    this.paymentInformation = null;
    this.customerAccount = null;
    this.address = null;
  }

  public String toString() {
    return super.toString() + "[" +
        "transactionId" + ":" + getTransactionId() + "," +
        "totalPrice" + ":" + getTotalPrice() + "," +
        "isPaid" + ":" + getIsPaid() + "," +
        "deliveryStatus" + ":" + getDeliveryStatus() + "," +
        "userAgreementCheck" + ":" + getUserAgreementCheck() + "]"
        + System.getProperties().getProperty("line.separator") +
        "  " + "paymentInformation = "
        + (getPaymentInformation() != null ? Integer.toHexString(System.identityHashCode(getPaymentInformation()))
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "customerAccount = "
        + (getCustomerAccount() != null ? Integer.toHexString(System.identityHashCode(getCustomerAccount())) : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "address = "
        + (getAddress() != null ? Integer.toHexString(System.identityHashCode(getAddress())) : "null");
  }
}