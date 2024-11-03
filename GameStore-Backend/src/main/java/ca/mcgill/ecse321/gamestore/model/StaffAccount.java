package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;

// line 11 "model.ump"
// line 147 "model.ump"
@Entity
public class StaffAccount extends Account {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public StaffAccount() {
  }

  public StaffAccount(String aUsername, String aPasswordHash, String aRandomPassword) {
    super(aUsername, aPasswordHash, aRandomPassword);
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}