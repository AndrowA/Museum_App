package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.*;

// line 55 "model.ump"
// line 151 "model.ump"
@Entity
public abstract class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Associations
  @OneToMany(mappedBy = "room")
  private List<Artifact> artifacts;
  @Id
  @GeneratedValue
  private Long roomId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room()
  {
    artifacts = new ArrayList<Artifact>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Artifact getArtifact(int index)
  {
    Artifact aArtifact = artifacts.get(index);
    return aArtifact;
  }

  public List<Artifact> getArtifacts()
  {
    List<Artifact> newArtifacts = Collections.unmodifiableList(artifacts);
    return newArtifacts;
  }

  public int numberOfArtifacts()
  {
    int number = artifacts.size();
    return number;
  }

  public boolean hasArtifacts()
  {
    boolean has = artifacts.size() > 0;
    return has;
  }

  public int indexOfArtifact(Artifact aArtifact)
  {
    int index = artifacts.indexOf(aArtifact);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtifacts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addArtifact(Artifact aArtifact)
  {
    boolean wasAdded = false;
    if (artifacts.contains(aArtifact)) { return false; }
    Room existingRoom = aArtifact.getRoom();
    if (existingRoom == null)
    {
      aArtifact.setRoom(this);
    }
    else if (!this.equals(existingRoom))
    {
      existingRoom.removeArtifact(aArtifact);
      addArtifact(aArtifact);
    }
    else
    {
      artifacts.add(aArtifact);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtifact(Artifact aArtifact)
  {
    boolean wasRemoved = false;
    if (artifacts.contains(aArtifact))
    {
      artifacts.remove(aArtifact);
      aArtifact.setRoom(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtifactAt(Artifact aArtifact, int index)
  {  
    boolean wasAdded = false;
    if(addArtifact(aArtifact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtifacts()) { index = numberOfArtifacts() - 1; }
      artifacts.remove(aArtifact);
      artifacts.add(index, aArtifact);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtifactAt(Artifact aArtifact, int index)
  {
    boolean wasAdded = false;
    if(artifacts.contains(aArtifact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtifacts()) { index = numberOfArtifacts() - 1; }
      artifacts.remove(aArtifact);
      artifacts.add(index, aArtifact);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtifactAt(aArtifact, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while( !artifacts.isEmpty() )
    {
      artifacts.get(0).setRoom(null);
    }
  }

  public void setRoomId(Long roomId) {
    this.roomId = roomId;
  }

  public Long getRoomId() {
    return roomId;
  }
}