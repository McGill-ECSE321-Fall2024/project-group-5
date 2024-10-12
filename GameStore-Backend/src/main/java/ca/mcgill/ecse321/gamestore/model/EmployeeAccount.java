package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;

// line 35 "model.ump"
// line 166 "model.ump"
@Entity
public class EmployeeAccount extends StaffAccount {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public EmployeeAccount() {
  }

  public EmployeeAccount(String aUsername, String aPasswordHash, String aRandomPassword) {
    super(aUsername, aPasswordHash, aRandomPassword);
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public void delete() {
    super.delete();
  }

}