package ca.mcgill.ecse321.gamestore.model;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.sql.Date;

// line 17 "model.ump"
// line 152 "model.ump"
@Entity
public class CustomerAccount extends Account {

  // ------------------------
  // ENUMERATIONS
  // ------------------------

  public enum CardType {
    Visa, Mastercard, Interact, AmericanExpress
  }

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // CustomerAccount Attributes
  private String emailAddress;
  private String name;

  // CustomerAccount Associations
  @OneToOne
  @JoinColumn(name = "wishlist d", referencedColumnName = "id")
  private Wishlist wishlist;
  @OneToMany(mappedBy = "customerAccount", cascade = CascadeType.ALL)
  private List<PaymentInformation> paymentInformations;
  @OneToMany(mappedBy = "customerAccount", cascade = CascadeType.ALL)
  private List<Review> reviews;
  @OneToMany(mappedBy = "customerAccount", cascade = CascadeType.ALL)
  private List<Transaction> transactions;
  @OneToMany(mappedBy = "customerAccount", cascade = CascadeType.ALL)
  private List<Cart> carts;
  @OneToMany(mappedBy = "customerAccount", cascade = CascadeType.ALL)
  private List<Address> addresses;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public CustomerAccount() {
  }

  public CustomerAccount(String aUsername, String aPasswordHash, String aRandomPassword, String aEmailAddress,
      Wishlist aWishlist) {
    super(aUsername, aPasswordHash, aRandomPassword);
    emailAddress = aEmailAddress;
    name = null;
    if (aWishlist == null || aWishlist.getCustomerAccount() != null) {
      throw new RuntimeException(
          "Unable to create CustomerAccount due to aWishlist. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    wishlist = aWishlist;
    paymentInformations = new ArrayList<PaymentInformation>();
    reviews = new ArrayList<Review>();
    transactions = new ArrayList<Transaction>();
    carts = new ArrayList<Cart>();
    addresses = new ArrayList<Address>();
  }

  public CustomerAccount(String aUsername, String aPasswordHash, String aRandomPassword, String aEmailAddress) {
    super(aUsername, aPasswordHash, aRandomPassword);
    emailAddress = aEmailAddress;
    name = null;
    wishlist = new Wishlist(this);
    paymentInformations = new ArrayList<PaymentInformation>();
    reviews = new ArrayList<Review>();
    transactions = new ArrayList<Transaction>();
    carts = new ArrayList<Cart>();
    addresses = new ArrayList<Address>();
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setName(String aName) {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public void setEmail (String email){
    this.emailAddress=email;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getName() {
    return name;
  }

  /* Code from template association_GetOne */
  public Wishlist getWishlist() {
    return wishlist;
  }

  /* Code from template association_GetMany */
  public PaymentInformation getPaymentInformation(int index) {
    PaymentInformation aPaymentInformation = paymentInformations.get(index);
    return aPaymentInformation;
  }

  public List<PaymentInformation> getPaymentInformations() {
    List<PaymentInformation> newPaymentInformations = Collections.unmodifiableList(paymentInformations);
    return newPaymentInformations;
  }

  public int numberOfPaymentInformations() {
    int number = paymentInformations.size();
    return number;
  }

  public boolean hasPaymentInformations() {
    boolean has = paymentInformations.size() > 0;
    return has;
  }

  public int indexOfPaymentInformation(PaymentInformation aPaymentInformation) {
    int index = paymentInformations.indexOf(aPaymentInformation);
    return index;
  }

  /* Code from template association_GetMany */
  public Review getReview(int index) {
    Review aReview = reviews.get(index);
    return aReview;
  }

  public List<Review> getReviews() {
    List<Review> newReviews = Collections.unmodifiableList(reviews);
    return newReviews;
  }

  public int numberOfReviews() {
    int number = reviews.size();
    return number;
  }

  public boolean hasReviews() {
    boolean has = reviews.size() > 0;
    return has;
  }

  public int indexOfReview(Review aReview) {
    int index = reviews.indexOf(aReview);
    return index;
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

  /* Code from template association_GetMany */
  public Cart getCart(int index) {
    Cart aCart = carts.get(index);
    return aCart;
  }

  public List<Cart> getCarts() {
    List<Cart> newCarts = Collections.unmodifiableList(carts);
    return newCarts;
  }

  public int numberOfCarts() {
    int number = carts.size();
    return number;
  }

  public boolean hasCarts() {
    boolean has = carts.size() > 0;
    return has;
  }

  public int indexOfCart(Cart aCart) {
    int index = carts.indexOf(aCart);
    return index;
  }

  /* Code from template association_GetMany */
  public Address getAddress(int index) {
    Address aAddress = addresses.get(index);
    return aAddress;
  }

  public List<Address> getAddresses() {
    List<Address> newAddresses = Collections.unmodifiableList(addresses);
    return newAddresses;
  }

  public int numberOfAddresses() {
    int number = addresses.size();
    return number;
  }

  public boolean hasAddresses() {
    boolean has = addresses.size() > 0;
    return has;
  }

  public int indexOfAddress(Address aAddress) {
    int index = addresses.indexOf(aAddress);
    return index;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPaymentInformations() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public PaymentInformation addPaymentInformation(String aCardholderName, int aCardNumber, Date aExpirationDate,
      int aCvc, PaymentInformation.CardType aCardType) {
    return new PaymentInformation(aCardholderName, aCardNumber, aExpirationDate, aCvc, aCardType, this);
  }

  public boolean addPaymentInformation(PaymentInformation aPaymentInformation) {
    boolean wasAdded = false;
    if (paymentInformations.contains(aPaymentInformation)) {
      return false;
    }
    CustomerAccount existingCustomerAccount = aPaymentInformation.getCustomerAccount();
    boolean isNewCustomerAccount = existingCustomerAccount != null && !this.equals(existingCustomerAccount);
    if (isNewCustomerAccount) {
      aPaymentInformation.setCustomerAccount(this);
    } else {
      paymentInformations.add(aPaymentInformation);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePaymentInformation(PaymentInformation aPaymentInformation) {
    boolean wasRemoved = false;
    // Unable to remove aPaymentInformation, as it must always have a
    // customerAccount
    if (!this.equals(aPaymentInformation.getCustomerAccount())) {
      paymentInformations.remove(aPaymentInformation);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addPaymentInformationAt(PaymentInformation aPaymentInformation, int index) {
    boolean wasAdded = false;
    if (addPaymentInformation(aPaymentInformation)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPaymentInformations()) {
        index = numberOfPaymentInformations() - 1;
      }
      paymentInformations.remove(aPaymentInformation);
      paymentInformations.add(index, aPaymentInformation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePaymentInformationAt(PaymentInformation aPaymentInformation, int index) {
    boolean wasAdded = false;
    if (paymentInformations.contains(aPaymentInformation)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPaymentInformations()) {
        index = numberOfPaymentInformations() - 1;
      }
      paymentInformations.remove(aPaymentInformation);
      paymentInformations.add(index, aPaymentInformation);
      wasAdded = true;
    } else {
      wasAdded = addPaymentInformationAt(aPaymentInformation, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReviews() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Review addReview(Date aDate, String aDescription, int aLikeCount, int aDislikeCount, float aRating,
      boolean aEmployeeReviewed, Game aGame) {
    return new Review(aDate, aDescription, aLikeCount, aDislikeCount, aRating, aEmployeeReviewed, this, aGame);
  }

  public boolean addReview(Review aReview) {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) {
      return false;
    }
    CustomerAccount existingCustomerAccount = aReview.getCustomerAccount();
    boolean isNewCustomerAccount = existingCustomerAccount != null && !this.equals(existingCustomerAccount);
    if (isNewCustomerAccount) {
      aReview.setCustomerAccount(this);
    } else {
      reviews.add(aReview);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReview(Review aReview) {
    boolean wasRemoved = false;
    // Unable to remove aReview, as it must always have a customerAccount
    if (!this.equals(aReview.getCustomerAccount())) {
      reviews.remove(aReview);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addReviewAt(Review aReview, int index) {
    boolean wasAdded = false;
    if (addReview(aReview)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfReviews()) {
        index = numberOfReviews() - 1;
      }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReviewAt(Review aReview, int index) {
    boolean wasAdded = false;
    if (reviews.contains(aReview)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfReviews()) {
        index = numberOfReviews() - 1;
      }
      reviews.remove(aReview);
      reviews.add(index, aReview);
      wasAdded = true;
    } else {
      wasAdded = addReviewAt(aReview, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTransactions() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Transaction addTransaction(double aTotalPrice, boolean aIsPaid, boolean aDeliveryStatus, String aPromotionCode,
      boolean aUserAgreementCheck, PaymentInformation aPaymentInformation, Cart aCart, Address aAddress) {
    return new Transaction(aTotalPrice, aIsPaid, aDeliveryStatus, aPromotionCode, aUserAgreementCheck,
        aPaymentInformation,
        this, aCart, aAddress);
  }

  public boolean addTransaction(Transaction aTransaction) {
    boolean wasAdded = false;
    if (transactions.contains(aTransaction)) {
      return false;
    }
    CustomerAccount existingCustomerAccount = aTransaction.getCustomerAccount();
    boolean isNewCustomerAccount = existingCustomerAccount != null && !this.equals(existingCustomerAccount);
    if (isNewCustomerAccount) {
      aTransaction.setCustomerAccount(this);
    } else {
      transactions.add(aTransaction);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTransaction(Transaction aTransaction) {
    boolean wasRemoved = false;
    // Unable to remove aTransaction, as it must always have a customerAccount
    if (!this.equals(aTransaction.getCustomerAccount())) {
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

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCarts() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Cart addCart(int aId) {
    return new Cart(aId, this);
  }

  public boolean addCart(Cart aCart) {
    boolean wasAdded = false;
    if (carts.contains(aCart)) {
      return false;
    }
    CustomerAccount existingCustomerAccount = aCart.getCustomerAccount();
    boolean isNewCustomerAccount = existingCustomerAccount != null && !this.equals(existingCustomerAccount);
    if (isNewCustomerAccount) {
      aCart.setCustomerAccount(this);
    } else {
      carts.add(aCart);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCart(Cart aCart) {
    boolean wasRemoved = false;
    // Unable to remove aCart, as it must always have a customerAccount
    if (!this.equals(aCart.getCustomerAccount())) {
      carts.remove(aCart);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addCartAt(Cart aCart, int index) {
    boolean wasAdded = false;
    if (addCart(aCart)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfCarts()) {
        index = numberOfCarts() - 1;
      }
      carts.remove(aCart);
      carts.add(index, aCart);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCartAt(Cart aCart, int index) {
    boolean wasAdded = false;
    if (carts.contains(aCart)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfCarts()) {
        index = numberOfCarts() - 1;
      }
      carts.remove(aCart);
      carts.add(index, aCart);
      wasAdded = true;
    } else {
      wasAdded = addCartAt(aCart, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAddresses() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Address addAddress(String aAddress, String aCity, String aProvince, String aCountry, String aPostalCode) {
    return new Address(aAddress, aCity, aProvince, aCountry, aPostalCode, this);
  }

  public boolean addAddress(Address aAddress) {
    boolean wasAdded = false;
    if (addresses.contains(aAddress)) {
      return false;
    }
    CustomerAccount existingCustomerAccount = aAddress.getCustomerAccount();
    boolean isNewCustomerAccount = existingCustomerAccount != null && !this.equals(existingCustomerAccount);
    if (isNewCustomerAccount) {
      aAddress.setCustomerAccount(this);
    } else {
      addresses.add(aAddress);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAddress(Address aAddress) {
    boolean wasRemoved = false;
    // Unable to remove aAddress, as it must always have a customerAccount
    if (!this.equals(aAddress.getCustomerAccount())) {
      addresses.remove(aAddress);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addAddressAt(Address aAddress, int index) {
    boolean wasAdded = false;
    if (addAddress(aAddress)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfAddresses()) {
        index = numberOfAddresses() - 1;
      }
      addresses.remove(aAddress);
      addresses.add(index, aAddress);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAddressAt(Address aAddress, int index) {
    boolean wasAdded = false;
    if (addresses.contains(aAddress)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfAddresses()) {
        index = numberOfAddresses() - 1;
      }
      addresses.remove(aAddress);
      addresses.add(index, aAddress);
      wasAdded = true;
    } else {
      wasAdded = addAddressAt(aAddress, index);
    }
    return wasAdded;
  }

  public void delete() {
    Wishlist existingWishlist = wishlist;
    wishlist = null;
    if (existingWishlist != null) {
      existingWishlist.delete();
    }
    for (int i = paymentInformations.size(); i > 0; i--) {
      PaymentInformation aPaymentInformation = paymentInformations.get(i - 1);
      aPaymentInformation.delete();
    }
    for (int i = reviews.size(); i > 0; i--) {
      Review aReview = reviews.get(i - 1);
      aReview.delete();
    }
    for (int i = transactions.size(); i > 0; i--) {
      Transaction aTransaction = transactions.get(i - 1);
      aTransaction.delete();
    }
    for (int i = carts.size(); i > 0; i--) {
      Cart aCart = carts.get(i - 1);
      aCart.delete();
    }
    for (int i = addresses.size(); i > 0; i--) {
      Address aAddress = addresses.get(i - 1);
      aAddress.delete();
    }
    super.delete();
  }

  public String toString() {
    return super.toString() + "[" +
        "emailAddress" + ":" + getEmailAddress() + "," +
        "name" + ":" + getName() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "wishlist = "
        + (getWishlist() != null ? Integer.toHexString(System.identityHashCode(getWishlist())) : "null");
  }
}