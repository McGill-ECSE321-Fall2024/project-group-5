/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 28 "model.ump"
// line 161 "model.ump"
public class ManagerAccount extends StaffAccount
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static ManagerAccount theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ManagerAccount Attributes
  private String username;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private ManagerAccount(String aUsername, String aPasswordHash, String aRandomPassword, int aId)
  {
    super(aUsername, aPasswordHash, aRandomPassword, aId);
    username = "admin";
  }

  public static ManagerAccount getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new ManagerAccount();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "]";
  }
}