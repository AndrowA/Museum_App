package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.*;

// line 49 "model.ump"
// line 144 "model.ump"
@Entity
public class Artifact
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artifact Attributes
  private String name;
  private String description;
  private String url;

  //Artifact Associations
  @Id
  @GeneratedValue
  private Long artifactId;
  @OneToOne(mappedBy = "artifact")
  private Loan loan;
  @ManyToOne(cascade = CascadeType.MERGE)
  private Room room;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artifact(String aName, String aDescription, String aUrl, Room aRoom, MyMuseum aMyMuseum)
  {
    name = aName;
    description = aDescription;
    url = aUrl;
    boolean didAddRoom = setRoom(aRoom);
    if (!didAddRoom)
    {
      throw new RuntimeException("Unable to create artifact due to room. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create artifact due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Artifact(){

  }
  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setUrl(String aUrl)
  {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public String getUrl()
  {
    return url;
  }
  /* Code from template association_GetOne */
  public Loan getLoan()
  {
    return loan;
  }

  public boolean hasLoan()
  {
    boolean has = loan != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }
  /* Code from template association_GetOne */
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setLoan(Loan aNewLoan)
  {
    boolean wasSet = false;
    if (loan != null && !loan.equals(aNewLoan) && equals(loan.getArtifact()))
    {
      //Unable to setLoan, as existing loan would become an orphan
      return wasSet;
    }

    loan = aNewLoan;
    Artifact anOldArtifact = aNewLoan != null ? aNewLoan.getArtifact() : null;

    if (!this.equals(anOldArtifact))
    {
      if (anOldArtifact != null)
      {
        anOldArtifact.loan = null;
      }
      if (loan != null)
      {
        loan.setArtifact(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setRoom(Room aRoom)
  {
    boolean wasSet = false;
    if (aRoom == null)
    {
      return wasSet;
    }

    Room existingRoom = room;
    room = aRoom;
    if (existingRoom != null && !existingRoom.equals(aRoom))
    {
      existingRoom.removeArtifact(this);
    }
    room.addArtifact(this);
    wasSet = true;
    return wasSet;
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
      existingMyMuseum.removeArtifact(this);
    }
    myMuseum.addArtifact(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Loan existingLoan = loan;
    loan = null;
    if (existingLoan != null)
    {
      existingLoan.delete();
    }
    Room placeholderRoom = room;
    this.room = null;
    if(placeholderRoom != null)
    {
      placeholderRoom.removeArtifact(this);
    }
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeArtifact(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "url" + ":" + getUrl()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "loan = "+(getLoan()!=null?Integer.toHexString(System.identityHashCode(getLoan())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "room = "+(getRoom()!=null?Integer.toHexString(System.identityHashCode(getRoom())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "myMuseum = "+(getMyMuseum()!=null?Integer.toHexString(System.identityHashCode(getMyMuseum())):"null");
  }
  public Long getArtifactId() {
    return artifactId;
  }

  public void setArtifactId(Long artifactId) {
    this.artifactId = artifactId;
  }
}