package ca.mcgill.ecse321.gamestore.model;

// line 85 "model.ump"
// line 202 "model.ump"
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Order {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Order Attributes
  @Id
  @GeneratedValue
  private int orderId;
  private double totalPrice;
  private boolean isPaid;
  private boolean deliveryStatus;
  private String promotionCode;
  private boolean userAgreementCheck;

  // Order Associations
  @ManyToOne
  @JoinColumn(name = "payment information id")
  private PaymentInformation paymentInformation;
  @ManyToOne
  @JoinColumn(name = "customer account id")
  private CustomerAccount customerAccount;
  @ManyToOne
  @JoinColumn(name = "cart id")
  private Cart cart;
  @ManyToOne
  @JoinColumn(name = "address id")
  private Address address;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Order(double aTotalPrice, boolean aIsPaid, boolean aDeliveryStatus, String aPromotionCode,
      boolean aUserAgreementCheck, PaymentInformation aPaymentInformation, CustomerAccount aCustomerAccount, Cart aCart,
      Address aAddress) {
    totalPrice = aTotalPrice;
    isPaid = aIsPaid;
    deliveryStatus = aDeliveryStatus;
    promotionCode = aPromotionCode;
    userAgreementCheck = aUserAgreementCheck;
    boolean didAddPaymentInformation = setPaymentInformation(aPaymentInformation);
    if (!didAddPaymentInformation) {
      throw new RuntimeException(
          "Unable to create order due to paymentInformation. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount) {
      throw new RuntimeException(
          "Unable to create order due to customerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCart = setCart(aCart);
    if (!didAddCart) {
      throw new RuntimeException(
          "Unable to create order due to cart. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAddress = setAddress(aAddress);
    if (!didAddAddress) {
      throw new RuntimeException(
          "Unable to create order due to address. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setPromotionCode(String aPromotionCode) {
    boolean wasSet = false;
    promotionCode = aPromotionCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setUserAgreementCheck(boolean aUserAgreementCheck) {
    boolean wasSet = false;
    userAgreementCheck = aUserAgreementCheck;
    wasSet = true;
    return wasSet;
  }

  public int getOrderId() {
    return orderId;
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

  public String getPromotionCode() {
    return promotionCode;
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
  public Cart getCart() {
    return cart;
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

    PaymentInformation existingPaymentInformation = paymentInformation;
    paymentInformation = aPaymentInformation;
    if (existingPaymentInformation != null && !existingPaymentInformation.equals(aPaymentInformation)) {
      existingPaymentInformation.removeOrder(this);
    }
    paymentInformation.addOrder(this);
    wasSet = true;
    return wasSet;
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
      existingCustomerAccount.removeOrder(this);
    }
    customerAccount.addOrder(this);
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetOneToOptionalOne */
  public boolean setCart(Cart aNewCart) {
    boolean wasSet = false;
    if (aNewCart == null) {
      // Unable to setCart to null, as order must always be associated to a cart
      return wasSet;
    }

    Order existingOrder = aNewCart.getOrder();
    if (existingOrder != null && !equals(existingOrder)) {
      // Unable to setCart, the current cart already has a order, which would be
      // orphaned if it were re-assigned
      return wasSet;
    }

    Cart anOldCart = cart;
    cart = aNewCart;
    cart.setOrder(this);

    if (anOldCart != null) {
      anOldCart.setOrder(null);
    }
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetOneToMany */
  public boolean setAddress(Address aAddress) {
    boolean wasSet = false;
    if (aAddress == null) {
      return wasSet;
    }

    Address existingAddress = address;
    address = aAddress;
    if (existingAddress != null && !existingAddress.equals(aAddress)) {
      existingAddress.removeOrder(this);
    }
    address.addOrder(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    PaymentInformation placeholderPaymentInformation = paymentInformation;
    this.paymentInformation = null;
    if (placeholderPaymentInformation != null) {
      placeholderPaymentInformation.removeOrder(this);
    }
    CustomerAccount placeholderCustomerAccount = customerAccount;
    this.customerAccount = null;
    if (placeholderCustomerAccount != null) {
      placeholderCustomerAccount.removeOrder(this);
    }
    Cart existingCart = cart;
    cart = null;
    if (existingCart != null) {
      existingCart.setOrder(null);
    }
    Address placeholderAddress = address;
    this.address = null;
    if (placeholderAddress != null) {
      placeholderAddress.removeOrder(this);
    }
  }

  public String toString() {
    return super.toString() + "[" +
        "orderId" + ":" + getOrderId() + "," +
        "totalPrice" + ":" + getTotalPrice() + "," +
        "isPaid" + ":" + getIsPaid() + "," +
        "deliveryStatus" + ":" + getDeliveryStatus() + "," +
        "promotionCode" + ":" + getPromotionCode() + "," +
        "userAgreementCheck" + ":" + getUserAgreementCheck() + "]"
        + System.getProperties().getProperty("line.separator") +
        "  " + "paymentInformation = "
        + (getPaymentInformation() != null ? Integer.toHexString(System.identityHashCode(getPaymentInformation()))
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "customerAccount = "
        + (getCustomerAccount() != null ? Integer.toHexString(System.identityHashCode(getCustomerAccount())) : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "cart = " + (getCart() != null ? Integer.toHexString(System.identityHashCode(getCart())) : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "address = "
        + (getAddress() != null ? Integer.toHexString(System.identityHashCode(getAddress())) : "null");
  }
}