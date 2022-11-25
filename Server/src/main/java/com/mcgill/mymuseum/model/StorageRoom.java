package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

// line 64 "model.ump"
// line 159 "model.ump"
@Entity
public class StorageRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //StorageRoom Associations
  @OneToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public StorageRoom(String aName, MyMuseum aMyMuseum)
  {
    super(aName);
    if (aMyMuseum == null || aMyMuseum.getStorageRoom() != null)
    {
      throw new RuntimeException("Unable to create StorageRoom due to aMyMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    myMuseum = aMyMuseum;
  }

  public StorageRoom(String aName, String aPricePerPassForMyMuseum, String aAddressForMyMuseum, President aPresidentForMyMuseum)
  {
    super(aName);
    myMuseum = new MyMuseum(aPricePerPassForMyMuseum, aAddressForMyMuseum, aPresidentForMyMuseum, this);
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