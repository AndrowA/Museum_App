package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.*;
import java.sql.Date;

// line 34 "model.ump"
// line 126 "model.ump"
@Entity
public class MuseumPass
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MuseumPass Attributes
  private Date passDate;

  //MuseumPass Associations
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Visitor owner;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;
  @Id
  @GeneratedValue
  private Long passId;
  private int passCost;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MuseumPass(Date aPassDate, Visitor aOwner, MyMuseum aMyMuseum)
  {
    passDate = aPassDate;
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create pass due to owner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create museumPass due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }
  public MuseumPass() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassDate(Date aPassDate)
  {
    boolean wasSet = false;
    passDate = aPassDate;
    wasSet = true;
    return wasSet;
  }

  public Date getPassDate()
  {
    return passDate;
  }
  /* Code from template association_GetOne */
  public Visitor getOwner()
  {
    return owner;
  }
  /* Code from template association_GetOne */
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }
  /* Code from template association_SetOneToMany */
  public boolean setOwner(Visitor aOwner)
  {
    boolean wasSet = false;
    if (aOwner == null)
    {
      return wasSet;
    }

    Visitor existingOwner = owner;
    owner = aOwner;
    if (existingOwner != null && !existingOwner.equals(aOwner))
    {
      existingOwner.removePass(this);
    }
    owner.addPass(this);
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
      existingMyMuseum.removeMuseumPass(this);
    }
    myMuseum.addMuseumPass(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Visitor placeholderOwner = owner;
    this.owner = null;
    if(placeholderOwner != null)
    {
      placeholderOwner.removePass(this);
    }
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeMuseumPass(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "passDate" + "=" + (getPassDate() != null ? !getPassDate().equals(this)  ? getPassDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "myMuseum = "+(getMyMuseum()!=null?Integer.toHexString(System.identityHashCode(getMyMuseum())):"null");
  }


  public void setPassId(Long passId) {
    this.passId = passId;
  }

  public Long getPassId() {
    return passId;
  }

  public void setPassCost(int passCost){
    this.passCost = passCost;
  }

  }



