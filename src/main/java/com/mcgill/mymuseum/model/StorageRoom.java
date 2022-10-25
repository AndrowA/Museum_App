package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;

// line 66 "model.ump"
// line 164 "model.ump"
@Entity
public class StorageRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StorageRoom Associations
  @Transient
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StorageRoom(MyMuseum aMyMuseum)
  {
    super();
    if (aMyMuseum == null || aMyMuseum.getStorageRooms() != null)
    {
      throw new RuntimeException("Unable to create StorageRoom due to aMyMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    myMuseum = aMyMuseum;
  }

  public StorageRoom(String aOpeningHoursForMyMuseum, President aPresidentForMyMuseum)
  {
    super();
    myMuseum = new MyMuseum(aOpeningHoursForMyMuseum, aPresidentForMyMuseum, this);
  }

  public StorageRoom() {

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