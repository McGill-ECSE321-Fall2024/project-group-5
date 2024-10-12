package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

// line 11 "model.ump"
// line 147 "model.ump"
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class StaffAccount extends Account {

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