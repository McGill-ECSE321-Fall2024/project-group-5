/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 96 "model.ump"
// line 208 "model.ump"
public class Review
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Review Attributes
  private Date date;
  private String description;
  private int likeCount;
  private int dislikeCount;
  private float rating;
  private boolean employeeReviewed;
  private int id;

  //Review Associations
  private List<Review> reply;
  private CustomerAccount customerAccount;
  private Review review;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Review(Date aDate, String aDescription, int aLikeCount, int aDislikeCount, float aRating, boolean aEmployeeReviewed, int aId, CustomerAccount aCustomerAccount, Game aGame)
  {
    date = aDate;
    description = aDescription;
    likeCount = aLikeCount;
    dislikeCount = aDislikeCount;
    rating = aRating;
    employeeReviewed = aEmployeeReviewed;
    id = aId;
    reply = new ArrayList<Review>();
    boolean didAddCustomerAccount = setCustomerAccount(aCustomerAccount);
    if (!didAddCustomerAccount)
    {
      throw new RuntimeException("Unable to create review due to customerAccount. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create review due to game. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setLikeCount(int aLikeCount)
  {
    boolean wasSet = false;
    likeCount = aLikeCount;
    wasSet = true;
    return wasSet;
  }

  public boolean setDislikeCount(int aDislikeCount)
  {
    boolean wasSet = false;
    dislikeCount = aDislikeCount;
    wasSet = true;
    return wasSet;
  }

  public boolean setRating(float aRating)
  {
    boolean wasSet = false;
    rating = aRating;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmployeeReviewed(boolean aEmployeeReviewed)
  {
    boolean wasSet = false;
    employeeReviewed = aEmployeeReviewed;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public String getDescription()
  {
    return description;
  }

  public int getLikeCount()
  {
    return likeCount;
  }

  public int getDislikeCount()
  {
    return dislikeCount;
  }

  public float getRating()
  {
    return rating;
  }

  public boolean getEmployeeReviewed()
  {
    return employeeReviewed;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isEmployeeReviewed()
  {
    return employeeReviewed;
  }
  /* Code from template association_GetMany */
  public Review getReply(int index)
  {
    Review aReply = reply.get(index);
    return aReply;
  }

  public List<Review> getReply()
  {
    List<Review> newReply = Collections.unmodifiableList(reply);
    return newReply;
  }

  public int numberOfReply()
  {
    int number = reply.size();
    return number;
  }

  public boolean hasReply()
  {
    boolean has = reply.size() > 0;
    return has;
  }

  public int indexOfReply(Review aReply)
  {
    int index = reply.indexOf(aReply);
    return index;
  }
  /* Code from template association_GetOne */
  public CustomerAccount getCustomerAccount()
  {
    return customerAccount;
  }
  /* Code from template association_GetOne */
  public Review getReview()
  {
    return review;
  }

  public boolean hasReview()
  {
    boolean has = review != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReply()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addReply(Review aReply)
  {
    boolean wasAdded = false;
    if (reply.contains(aReply)) { return false; }
    Review existingReview = aReply.getReview();
    if (existingReview == null)
    {
      aReply.setReview(this);
    }
    else if (!this.equals(existingReview))
    {
      existingReview.removeReply(aReply);
      addReply(aReply);
    }
    else
    {
      reply.add(aReply);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReply(Review aReply)
  {
    boolean wasRemoved = false;
    if (reply.contains(aReply))
    {
      reply.remove(aReply);
      aReply.setReview(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReplyAt(Review aReply, int index)
  {  
    boolean wasAdded = false;
    if(addReply(aReply))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReply()) { index = numberOfReply() - 1; }
      reply.remove(aReply);
      reply.add(index, aReply);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReplyAt(Review aReply, int index)
  {
    boolean wasAdded = false;
    if(reply.contains(aReply))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReply()) { index = numberOfReply() - 1; }
      reply.remove(aReply);
      reply.add(index, aReply);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReplyAt(aReply, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomerAccount(CustomerAccount aCustomerAccount)
  {
    boolean wasSet = false;
    if (aCustomerAccount == null)
    {
      return wasSet;
    }

    CustomerAccount existingCustomerAccount = customerAccount;
    customerAccount = aCustomerAccount;
    if (existingCustomerAccount != null && !existingCustomerAccount.equals(aCustomerAccount))
    {
      existingCustomerAccount.removeReview(this);
    }
    customerAccount.addReview(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setReview(Review aReview)
  {
    boolean wasSet = false;
    Review existingReview = review;
    review = aReview;
    if (existingReview != null && !existingReview.equals(aReview))
    {
      existingReview.removeReply(this);
    }
    if (aReview != null)
    {
      aReview.addReply(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeReview(this);
    }
    game.addReview(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while( !reply.isEmpty() )
    {
      reply.get(0).setReview(null);
    }
    CustomerAccount placeholderCustomerAccount = customerAccount;
    this.customerAccount = null;
    if(placeholderCustomerAccount != null)
    {
      placeholderCustomerAccount.removeReview(this);
    }
    if (review != null)
    {
      Review placeholderReview = review;
      this.review = null;
      placeholderReview.removeReply(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeReview(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "description" + ":" + getDescription()+ "," +
            "likeCount" + ":" + getLikeCount()+ "," +
            "dislikeCount" + ":" + getDislikeCount()+ "," +
            "rating" + ":" + getRating()+ "," +
            "employeeReviewed" + ":" + getEmployeeReviewed()+ "," +
            "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "customerAccount = "+(getCustomerAccount()!=null?Integer.toHexString(System.identityHashCode(getCustomerAccount())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}