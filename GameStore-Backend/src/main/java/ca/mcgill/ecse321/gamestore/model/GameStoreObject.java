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
  private String name;
  private String description;
  private double price;
  private String policy;

  @Id
  @GeneratedValue
  private int id;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------
  public GameStoreObject() {
  }

  public GameStoreObject(String name, String description, double price, String policy) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.policy = policy;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

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
        "name" + ":" + getName() + "," +
        "description" + ":" + getDescription() + "," +
        "price" + ":" + getPrice() + "," +
        "policy" + ":" + getPolicy() + "," +
        "id" + ":" + getId() + "]";
  }
}
