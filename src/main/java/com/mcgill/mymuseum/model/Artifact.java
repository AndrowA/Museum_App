package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.*;

// line 60 "model.ump"
// line 156 "model.ump"
@Entity
public class Artifact
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------



  //Artifact Associations
  @Id
  @GeneratedValue
  private Long artifactId;
  @OneToOne(mappedBy = "artifact")
  private Loan loan;
  @ManyToOne
  private Room room;
  @Transient
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artifact(MyMuseum aMyMuseum)
  {
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create artifact due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Artifact() {

  }

  //------------------------
  // INTERFACE
  //------------------------
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

  public boolean hasRoom()
  {
    boolean has = room != null;
    return has;
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
  /* Code from template association_SetOptionalOneToMany */
  public boolean setRoom(Room aRoom)
  {
    boolean wasSet = false;
    Room existingRoom = room;
    room = aRoom;
    if (existingRoom != null && !existingRoom.equals(aRoom))
    {
      existingRoom.removeArtifact(this);
    }
    if (aRoom != null)
    {
      aRoom.addArtifact(this);
    }
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
    if (room != null)
    {
      Room placeholderRoom = room;
      this.room = null;
      placeholderRoom.removeArtifact(this);
    }
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeArtifact(this);
    }
  }
  public Long getArtifactId() {
    return artifactId;
  }

  public void setArtifactId(Long artifactId) {
    this.artifactId = artifactId;
  }

}