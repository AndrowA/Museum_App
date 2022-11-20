package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

// line 69 "model.ump"
// line 164 "model.ump"
@Entity
public class DisplayRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DisplayRoom Attributes
  private int roomCapacity;

  //DisplayRoom Associations
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DisplayRoom(String aName, int aRoomCapacity, MyMuseum aMyMuseum)
  {
    super(aName);
    roomCapacity = aRoomCapacity;
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create displayRoom due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public DisplayRoom() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomCapacity(int aRoomCapacity)
  {
    boolean wasSet = false;
    roomCapacity = aRoomCapacity;
    wasSet = true;
    return wasSet;
  }

  public int getRoomCapacity()
  {
    return roomCapacity;
  }
  /* Code from template association_GetOne */
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMyMuseum(MyMuseum aMyMuseum)
  {
    boolean wasSet = false;
    if (aMyMuseum == null)
    {
      return wasSet;
    }

    MyMuseum existingMyMuseum = myMuseum;
    myMuseum = aMyMuseum;
    if (existingMyMuseum != null && !existingMyMuseum.equals(aMyMuseum))
    {
      existingMyMuseum.removeDisplayRoom(this);
    }
    myMuseum.addDisplayRoom(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeDisplayRoom(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "roomCapacity" + ":" + getRoomCapacity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "myMuseum = "+(getMyMuseum()!=null?Integer.toHexString(System.identityHashCode(getMyMuseum())):"null");
  }
}