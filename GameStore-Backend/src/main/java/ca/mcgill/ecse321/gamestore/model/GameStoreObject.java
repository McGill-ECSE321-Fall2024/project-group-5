package ca.mcgill.ecse321.gamestore.model;

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

  public GameStoreObject(String policy) {
    this.policy = policy;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setPolicy(String policy) {
    boolean wasSet = false;
    this.policy = policy;
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

  @Override
  public String toString() {
    return super.toString() + "[" +
        "policy" + ":" + getPolicy() + "," +
        "id" + ":" + getId() + "]";
  }
}
