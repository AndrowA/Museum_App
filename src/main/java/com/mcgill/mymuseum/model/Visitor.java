package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.*;
import java.sql.Date;

// line 20 "model.ump"
// line 109 "model.ump"
@Entity
public class Visitor extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Attributes
  private boolean isBanned;

  //Visitor Associations
  @OneToMany(mappedBy = "loanee")
  private List<Loan> loan;
  @OneToMany(mappedBy = "owner")
  private List<MuseumPass> passes;
  @Transient
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Visitor(String aEmail, String aPassword, boolean aIsBanned, MyMuseum aMyMuseum)
  {
    super(aEmail, aPassword);
    isBanned = aIsBanned;
    loan = new ArrayList<Loan>();
    passes = new ArrayList<MuseumPass>();
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create visitor due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Visitor() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsBanned(boolean aIsBanned)
  {
    boolean wasSet = false;
    isBanned = aIsBanned;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsBanned()
  {
    return isBanned;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsBanned()
  {
    return isBanned;
  }
  /* Code from template association_GetMany */
  public Loan getLoan(int index)
  {
    Loan aLoan = loan.get(index);
    return aLoan;
  }

  public List<Loan> getLoan()
  {
    List<Loan> newLoan = Collections.unmodifiableList(loan);
    return newLoan;
  }

  public int numberOfLoan()
  {
    int number = loan.size();
    return number;
  }

  public boolean hasLoan()
  {
    boolean has = loan.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loan.indexOf(aLoan);
    return index;
  }
  /* Code from template association_GetMany */
  public MuseumPass getPass(int index)
  {
    MuseumPass aPass = passes.get(index);
    return aPass;
  }

  public List<MuseumPass> getPasses()
  {
    List<MuseumPass> newPasses = Collections.unmodifiableList(passes);
    return newPasses;
  }

  public int numberOfPasses()
  {
    int number = passes.size();
    return number;
  }

  public boolean hasPasses()
  {
    boolean has = passes.size() > 0;
    return has;
  }

  public int indexOfPass(MuseumPass aPass)
  {
    int index = passes.indexOf(aPass);
    return index;
  }
  /* Code from template association_GetOne */
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoan()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfLoan()
  {
    return 3;
  }
  /* Code from template association_AddOptionalNToOne */
  public Loan addLoan(Date aStartDate, Date aEndDate, Loan.LoanStatus aLoanStatus, MyMuseum aMyMuseum, Artifact aArtifact)
  {
    if (numberOfLoan() >= maximumNumberOfLoan())
    {
      return null;
    }
    else
    {
      return new Loan(aStartDate, aEndDate, aLoanStatus, this, aMyMuseum, aArtifact);
    }
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loan.contains(aLoan)) { return false; }
    if (numberOfLoan() >= maximumNumberOfLoan())
    {
      return wasAdded;
    }

    Visitor existingLoanee = aLoan.getLoanee();
    boolean isNewLoanee = existingLoanee != null && !this.equals(existingLoanee);
    if (isNewLoanee)
    {
      aLoan.setLoanee(this);
    }
    else
    {
      loan.add(aLoan);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoan, as it must always have a loanee
    if (!this.equals(aLoan.getLoanee()))
    {
      loan.remove(aLoan);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoanAt(Loan aLoan, int index)
  {  
    boolean wasAdded = false;
    if(addLoan(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoan()) { index = numberOfLoan() - 1; }
      loan.remove(aLoan);
      loan.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loan.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoan()) { index = numberOfLoan() - 1; }
      loan.remove(aLoan);
      loan.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPasses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MuseumPass addPass(int aPassCost, Date aPassDate, MyMuseum aMyMuseum)
  {
    return new MuseumPass(aPassCost, aPassDate, this, aMyMuseum);
  }

  public boolean addPass(MuseumPass aPass)
  {
    boolean wasAdded = false;
    if (passes.contains(aPass)) { return false; }
    Visitor existingOwner = aPass.getOwner();
    boolean isNewOwner = existingOwner != null && !this.equals(existingOwner);
    if (isNewOwner)
    {
      aPass.setOwner(this);
    }
    else
    {
      passes.add(aPass);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePass(MuseumPass aPass)
  {
    boolean wasRemoved = false;
    //Unable to remove aPass, as it must always have a owner
    if (!this.equals(aPass.getOwner()))
    {
      passes.remove(aPass);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPassAt(MuseumPass aPass, int index)
  {  
    boolean wasAdded = false;
    if(addPass(aPass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPasses()) { index = numberOfPasses() - 1; }
      passes.remove(aPass);
      passes.add(index, aPass);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePassAt(MuseumPass aPass, int index)
  {
    boolean wasAdded = false;
    if(passes.contains(aPass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPasses()) { index = numberOfPasses() - 1; }
      passes.remove(aPass);
      passes.add(index, aPass);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPassAt(aPass, index);
    }
    return wasAdded;
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
      existingMyMuseum.removeVisitor(this);
    }
    myMuseum.addVisitor(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=loan.size(); i > 0; i--)
    {
      Loan aLoan = loan.get(i - 1);
      aLoan.delete();
    }
    for(int i=passes.size(); i > 0; i--)
    {
      MuseumPass aPass = passes.get(i - 1);
      aPass.delete();
    }
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeVisitor(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isBanned" + ":" + getIsBanned()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "myMuseum = "+(getMyMuseum()!=null?Integer.toHexString(System.identityHashCode(getMyMuseum())):"null");
  }
}