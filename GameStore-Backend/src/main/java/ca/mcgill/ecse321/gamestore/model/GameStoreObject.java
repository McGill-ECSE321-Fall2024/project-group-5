package ca.mcgill.ecse321.gamestore.model;

import jakarta.persistence.CascadeType;

// line 125 "model.ump"
// line 222 "model.ump"

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.*;

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

  @OneToMany(mappedBy = "gameStoreObject", cascade = CascadeType.ALL)
  private List<PromotionCode> promotionCodes = new ArrayList<>();

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

  /* Code from template association_GetMany */
  public PromotionCode getPromotionCode(int index) {
    PromotionCode aPromotionCode = promotionCodes.get(index);
    return aPromotionCode;
  }

  public List<PromotionCode> getPromotionCodes() {
    List<PromotionCode> newPromotionCodes = Collections.unmodifiableList(promotionCodes);
    return newPromotionCodes;
  }

  public int numberOfPromotionCodes() {
    int number = promotionCodes.size();
    return number;
  }

  public boolean hasPromotionCodes() {
    boolean has = promotionCodes.size() > 0;
    return has;
  }

  public int indexOfPromotionCode(PromotionCode aPromotionCode) {
    int index = promotionCodes.indexOf(aPromotionCode);
    return index;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPromotionCodes() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public PromotionCode addPromotionCode(String aCode, int aPercentageValue, int aId) {
    return new PromotionCode(aCode, aPercentageValue, aId, this);
  }

  public boolean addPromotionCode(PromotionCode aPromotionCode) {
    boolean wasAdded = false;
    if (promotionCodes.contains(aPromotionCode)) {
      return false;
    }
    GameStoreObject existingGameStoreObject = aPromotionCode.getGameStoreObject();
    boolean isNewGameStoreObject = existingGameStoreObject != null
        && !this.equals(existingGameStoreObject);
    if (isNewGameStoreObject) {
      aPromotionCode.setGameStoreObject(this);
    } else {
      promotionCodes.add(aPromotionCode);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePromotionCode(PromotionCode aPromotionCode) {
    boolean wasRemoved = false;
    // Unable to remove aPromotionCode, as it must always have a
    // gameStoreObject
    if (!this.equals(aPromotionCode.getGameStoreObject())) {
      promotionCodes.remove(aPromotionCode);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addPromotionCodeAt(PromotionCode aPromotionCode, int index) {
    boolean wasAdded = false;
    if (addPromotionCode(aPromotionCode)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPromotionCodes()) {
        index = numberOfPromotionCodes() - 1;
      }
      promotionCodes.remove(aPromotionCode);
      promotionCodes.add(index, aPromotionCode);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePromotionCodeAt(PromotionCode aPromotionCode, int index) {
    boolean wasAdded = false;
    if (promotionCodes.contains(aPromotionCode)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPromotionCodes()) {
        index = numberOfPromotionCodes() - 1;
      }
      promotionCodes.remove(aPromotionCode);
      promotionCodes.add(index, aPromotionCode);
      wasAdded = true;
    } else {
      wasAdded = addPromotionCodeAt(aPromotionCode, index);
    }
    return wasAdded;
  }

  public void delete() {
    for (int i = promotionCodes.size(); i > 0; i--) {
      PromotionCode aPromotionCode = promotionCodes.get(i - 1);
      aPromotionCode.delete();
    }
  }

  public String toString() {
    return super.toString() + "[" +
        "policy" + ":" + getPolicy() + "," +
        "id" + ":" + getId() + "]";
  }
}