/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 2 "model.ump"
// line 142 "model.ump"
public abstract class Account
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Account> accountsByUsername = new HashMap<String, Account>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  private String username;
  private String passwordHash;
  private String randomPassword;
  private int id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String aUsername, String aPasswordHash, String aRandomPassword, int aId)
  {
    passwordHash = aPasswordHash;
    randomPassword = aRandomPassword;
    id = aId;
    if (!setUsername(aUsername))
    {
      throw new RuntimeException("Cannot create due to duplicate username. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    String anOldUsername = getUsername();
    if (anOldUsername != null && anOldUsername.equals(aUsername)) {
      return true;
    }
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      accountsByUsername.remove(anOldUsername);
    }
    accountsByUsername.put(aUsername, this);
    return wasSet;
  }

  public boolean setPasswordHash(String aPasswordHash)
  {
    boolean wasSet = false;
    passwordHash = aPasswordHash;
    wasSet = true;
    return wasSet;
  }

  public boolean setRandomPassword(String aRandomPassword)
  {
    boolean wasSet = false;
    randomPassword = aRandomPassword;
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

  public String getUsername()
  {
    return username;
  }
  /* Code from template attribute_GetUnique */
  public static Account getWithUsername(String aUsername)
  {
    return accountsByUsername.get(aUsername);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
  }

  public String getPasswordHash()
  {
    return passwordHash;
  }

  public String getRandomPassword()
  {
    return randomPassword;
  }

  public int getId()
  {
    return id;
  }

  public void delete()
  {
    accountsByUsername.remove(getUsername());
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "passwordHash" + ":" + getPasswordHash()+ "," +
            "randomPassword" + ":" + getRandomPassword()+ "," +
            "id" + ":" + getId()+ "]";
  }
}