package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.*;
import java.sql.Date;

// line 9 "model.ump"
// line 101 "model.ump"
@Entity
public class Visitor extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Visitor Associations
  @OneToMany(mappedBy = "loanee")
  private List<Loan> loans;
  @OneToMany(mappedBy = "owner")
  private List<MuseumPass> passes;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Visitor(String aEmail, String aPassword, MyMuseum aMyMuseum)
  {
    super(aEmail, aPassword);
    passes = new ArrayList<MuseumPass>();
    loans = new ArrayList<Loan>();
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create visitor due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Visitor() {
    super(null, null);
    passes = new ArrayList<MuseumPass>();
    loans = new ArrayList<Loan>();
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  /* Code from template association_GetMany */
  public Loan getLoan(int index)
  {
    Loan aLoan = loans.get(index);
    return aLoan;
  }

  public List<Loan> getLoans()
  {
    List<Loan> newLoans = Collections.unmodifiableList(loans);
    return newLoans;
  }

  public int numberOfLoans()
  {
    int number = loans.size();
    return number;
  }

  public boolean hasLoans()
  {
    boolean has = loans.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loans.indexOf(aLoan);
    return index;
  }
  /* Code from template association_GetOne */
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPasses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MuseumPass addPass(Date aPassDate, MyMuseum aMyMuseum)
  {
    return new MuseumPass(aPassDate, this, aMyMuseum);
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfLoans()
  {
    return 3;
  }
  /* Code from template association_AddOptionalNToOne */
  public Loan addLoan(Date aStartDate, Date aEndDate, Loan.LoanStatus aLoanStatus, Artifact aArtifact, MyMuseum aMyMuseum)
  {
    if (numberOfLoans() >= maximumNumberOfLoans())
    {
      return null;
    }
    else
    {
      return new Loan(aStartDate, aEndDate, aLoanStatus, this, aArtifact, aMyMuseum);
    }
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    if (numberOfLoans() >= maximumNumberOfLoans())
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
      loans.add(aLoan);
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
      loans.remove(aLoan);
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
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loans.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
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
    for(int i=passes.size(); i > 0; i--)
    {
      MuseumPass aPass = passes.get(i - 1);
      aPass.delete();
    }
    for(int i=loans.size(); i > 0; i--)
    {
      Loan aLoan = loans.get(i - 1);
      aLoan.delete();
    }
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeVisitor(this);
    }
    super.delete();
  }

}