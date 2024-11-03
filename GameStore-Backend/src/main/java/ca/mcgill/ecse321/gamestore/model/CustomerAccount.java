package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;

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

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public CustomerAccount() {
  }

  public CustomerAccount(String aUsername, String aPasswordHash, String aRandomPassword, String aEmailAddress) {
    super(aUsername, aPasswordHash, aRandomPassword);
    emailAddress = aEmailAddress;
    name = null;
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

  public void setEmail(String email) {
    this.emailAddress = email;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getName() {
    return name;
  }

  public void delete() {
    super.delete();
  }

  public String toString() {
    return super.toString() + "[" +
        "emailAddress" + ":" + getEmailAddress() + "," +
        "name" + ":" + getName() + "]";
  }
}