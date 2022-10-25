package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.*;

// line 71 "model.ump"
// line 169 "model.ump"
@Entity
public class DisplayRoom extends Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DisplayRoom Attributes
  private int roomCapacity;

  //DisplayRoom Associations
  @Transient
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DisplayRoom(int aRoomCapacity, MyMuseum aMyMuseum)
  {
    super();
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
  /* Code from template association_SetOneToAtMostN */
  public boolean setMyMuseum(MyMuseum aMyMuseum)
  {
    boolean wasSet = false;
    //Must provide myMuseum to displayRoom
    if (aMyMuseum == null)
    {
      return wasSet;
    }

    //myMuseum already at maximum (10)
    if (aMyMuseum.numberOfDisplayRooms() >= MyMuseum.maximumNumberOfDisplayRooms())
    {
      return wasSet;
    }
    
    MyMuseum existingMyMuseum = myMuseum;
    myMuseum = aMyMuseum;
    if (existingMyMuseum != null && !existingMyMuseum.equals(aMyMuseum))
    {
      boolean didRemove = existingMyMuseum.removeDisplayRoom(this);
      if (!didRemove)
      {
        myMuseum = existingMyMuseum;
        return wasSet;
      }
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