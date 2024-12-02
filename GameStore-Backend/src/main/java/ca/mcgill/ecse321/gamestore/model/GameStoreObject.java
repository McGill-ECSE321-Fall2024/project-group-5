package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class GameStoreObject {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // GameStoreObject Attributes
  private String policy;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_store_object_seq")
  @SequenceGenerator(name = "game_store_object_seq", sequenceName = "game_store_object_seq", allocationSize = 1)
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
