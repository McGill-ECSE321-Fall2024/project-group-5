/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 11 "model.ump"
// line 147 "model.ump"
public abstract class StaffAccount extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StaffAccount(String aUsername, String aPasswordHash, String aRandomPassword, int aId)
  {
    super(aUsername, aPasswordHash, aRandomPassword, aId);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}