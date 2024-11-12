package ca.mcgill.ecse321.gamestore.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

// line 138 "model.ump"
// line 242 "model.ump"
@Entity
public class PromotionCode {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // PromotionCode Attributes
  private String code;
  private int percentageValue;
  private Date expirationDate;
  @Id
  @GeneratedValue
  private int id;

  // PromotionCode Associations
  @ManyToOne
  @JoinColumn(name = "game store object id")
  private GameStoreObject gameStoreObject;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public PromotionCode() {
  }

  public PromotionCode(String aCode, int aPercentageValue, int aId, GameStoreObject aGameStoreObject,
      Date aExpirationDate) {
    code = aCode;
    percentageValue = aPercentageValue;
    id = aId;
    expirationDate = aExpirationDate;
    boolean didAddGameStoreObject = setGameStoreObject(aGameStoreObject);
    if (!didAddGameStoreObject) {
      throw new RuntimeException(
          "Unable to create promotionCode due to gameStoreObject. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setCode(String aCode) {
    boolean wasSet = false;
    code = aCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setPercentageValue(int aPercentageValue) {
    boolean wasSet = false;
    percentageValue = aPercentageValue;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpirationDate(Date aExpirationDate) {
    boolean wasSet = false;
    expirationDate = aExpirationDate;
    wasSet = true;
    return wasSet;
  }

  public String getCode() {
    return code;
  }

  public int getPercentageValue() {
    return percentageValue;
  }

  public int getId() {
    return id;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  /* Code from template association_GetOne */
  public GameStoreObject getGameStoreObject() {
    return gameStoreObject;
  }

  /* Code from template association_SetOneToMany */
  public boolean setGameStoreObject(GameStoreObject aGameStoreObject) {
    boolean wasSet = false;
    if (aGameStoreObject == null) {
      return wasSet;
    }
    gameStoreObject = aGameStoreObject;
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    this.gameStoreObject = null;
  }

  public String toString() {
    return super.toString() + "[" +
        "code" + ":" + getCode() + "," +
        "percentageValue" + ":" + getPercentageValue() + "," +
        "id" + ":" + getId() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "gameStoreObject = "
        + (getGameStoreObject() != null ? Integer.toHexString(System.identityHashCode(getGameStoreObject())) : "null");
  }
}