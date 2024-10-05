/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



// line 125 "model.ump"
// line 222 "model.ump"
public class GameStoreApplication
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameStoreApplication Attributes
  private String policy;
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameStoreApplication(String aPolicy, int aId)
  {
    policy = aPolicy;
    id = aId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPolicy(String aPolicy)
  {
    boolean wasSet = false;
    policy = aPolicy;
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

  public String getPolicy()
  {
    return policy;
  }

  public int getId()
  {
    return id;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "policy" + ":" + getPolicy()+ "," +
            "id" + ":" + getId()+ "]";
  }
}