package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;

// line 28 "model.ump"
// line 161 "model.ump"
@Entity
public class ManagerAccount extends StaffAccount {

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public ManagerAccount() {
  }

  public ManagerAccount(String aUsername, String aPasswordHash, String aRandomPassword) {
    super(aUsername, aPasswordHash, aRandomPassword);
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

  public String toString() {
    return super.toString() + "[" +
        "username" + ":" + getUsername() + "]";
  }
}