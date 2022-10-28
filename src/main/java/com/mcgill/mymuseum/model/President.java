package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

// line 22 "model.ump"
// line 116 "model.ump"
@Entity
public class President extends Employee
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //President Associations
  @OneToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public President(String aEmail, String aPassword, double aHourlyWage, double aOverTimeHourlyWage, MyMuseum aMyMuseum)
  {
    super(aEmail, aPassword, aHourlyWage, aOverTimeHourlyWage, aMyMuseum);
    if (aMyMuseum == null || aMyMuseum.getPresident() != null)
    {
      throw new RuntimeException("Unable to create President due to aMyMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    myMuseum = aMyMuseum;
  }

  public President(String aEmail, String aPassword, double aHourlyWage, double aOverTimeHourlyWage, MyMuseum aMyMuseum, String aPricePerPassForMyMuseum, String aAddressForMyMuseum, StorageRoom aStorageRoomForMyMuseum)
  {
    super(aEmail, aPassword, aHourlyWage, aOverTimeHourlyWage, aMyMuseum);
    myMuseum = new MyMuseum(aPricePerPassForMyMuseum, aAddressForMyMuseum, this, aStorageRoomForMyMuseum);
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



