package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;

// line 28 "model.ump"
// line 161 "model.ump"
@Entity
public class ManagerAccount extends StaffAccount {

  // ------------------------
  // STATIC VARIABLES
  // ------------------------

  private static ManagerAccount theInstance = null;

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ManagerAccount Attributes
  private String username;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  private ManagerAccount(String aUsername, String aPasswordHash, String aRandomPassword) {
    super(aUsername, aPasswordHash, aRandomPassword);
    username = "admin";
  }

  public static ManagerAccount getInstance() {
    if (theInstance == null) {
      return null;
    }
    return theInstance;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setUsername(String aUsername) {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public String getUsername() {
    return username;
  }

  public void delete() {
    super.delete();
  }

  public String toString() {
    return super.toString() + "[" +
        "username" + ":" + getUsername() + "]";
  }
}