package ca.mcgill.ecse321.gamestore.model;

import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

// line 2 "model.ump"
// line 142 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {

  // ------------------------
  // STATIC VARIABLES
  // ------------------------

  private static Map<String, Account> accountsByUsername = new HashMap<String, Account>();

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Account Attributes
  private String username;
  private String passwordHash;
  private String randomPassword;
  @Id
  @GeneratedValue
  private int id;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public Account() {
  }

  public Account(String aUsername, String aPasswordHash, String aRandomPassword) {
    passwordHash = aPasswordHash;
    randomPassword = aRandomPassword;
    if (!setUsername(aUsername)) {
      throw new RuntimeException(
          "Cannot create due to duplicate username. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setUsername(String aUsername) {
    boolean wasSet = false;
    String anOldUsername = getUsername();
    if (anOldUsername != null && anOldUsername.equals(aUsername)) {
      return true;
    }
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      accountsByUsername.remove(anOldUsername);
    }
    accountsByUsername.put(aUsername, this);
    return wasSet;
  }

  public boolean setPasswordHash(String aPasswordHash) {
    boolean wasSet = false;
    passwordHash = aPasswordHash;
    wasSet = true;
    return wasSet;
  }

  public boolean setRandomPassword(String aRandomPassword) {
    boolean wasSet = false;
    randomPassword = aRandomPassword;
    wasSet = true;
    return wasSet;
  }

  public String getUsername() {
    return username;
  }

  /* Code from template attribute_GetUnique */
  public static Account getWithUsername(String aUsername) {
    return accountsByUsername.get(aUsername);
  }

  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername) {
    return getWithUsername(aUsername) != null;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public String getRandomPassword() {
    return randomPassword;
  }

  public int getId() {
    return id;
  }

  public void delete() {
    accountsByUsername.remove(getUsername());
  }

  public String toString() {
    return super.toString() + "[" +
        "username" + ":" + getUsername() + "," +
        "passwordHash" + ":" + getPasswordHash() + "," +
        "randomPassword" + ":" + getRandomPassword() + "," +
        "id" + ":" + getId() + "]";
  }
}