package ca.mcgill.ecse321.gamestore.model;

// line 125 "model.ump"
// line 222 "model.ump"

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class GameStoreObject {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // GameStoreObject Attributes
  private String policy;
  @Id
  @GeneratedValue
  private int id;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public GameStoreObject() {
  }

  public GameStoreObject(String aPolicy) {
    policy = aPolicy;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setPolicy(String aPolicy) {
    boolean wasSet = false;
    policy = aPolicy;
    wasSet = true;
    return wasSet;
  }

  public String getPolicy() {
    return policy;
  }

  public int getId() {
    return id;
  }

  public void delete() {
  }

  public String toString() {
    return super.toString() + "[" +
        "policy" + ":" + getPolicy() + "," +
        "id" + ":" + getId() + "]";
  }
}