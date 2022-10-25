package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;

// line 77 "model.ump"
// line 174 "model.ump"
@Entity
public class President extends Employee
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //President Associations
  @Transient
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public President(String aEmail, String aPassword, int aHourlyWage, int aOvertimeHourlyWage, MyMuseum aApplication, MyMuseum aMyMuseum)
  {
    super(aEmail, aPassword, aHourlyWage, aOvertimeHourlyWage, aApplication);
    if (aMyMuseum == null || aMyMuseum.getPresident() != null)
    {
      throw new RuntimeException("Unable to create President due to aMyMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    myMuseum = aMyMuseum;
  }

  public President(String aEmail, String aPassword, int aHourlyWage, int aOvertimeHourlyWage, MyMuseum aApplication, String aOpeningHoursForMyMuseum, StorageRoom aStorageRoomsForMyMuseum)
  {
    super(aEmail, aPassword, aHourlyWage, aOvertimeHourlyWage, aApplication);
    myMuseum = new MyMuseum(aOpeningHoursForMyMuseum, this, aStorageRoomsForMyMuseum);
  }

  public President() {

  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }

  public void delete()
  {
    MyMuseum existingMyMuseum = myMuseum;
    myMuseum = null;
    if (existingMyMuseum != null)
    {
      existingMyMuseum.delete();
    }
    super.delete();
  }

}