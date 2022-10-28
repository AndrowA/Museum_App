package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.*;
import java.sql.Date;

// line 40 "model.ump"
// line 135 "model.ump"
@Entity
public class Loan
{
  @Id
  @GeneratedValue
  private Long loanId;


  public void setLoanId(Long loanId) {
    this.loanId = loanId;
  }

  public Long getLoanId() {
    return loanId;
  }

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum LoanStatus { Requested, InReview, Approved, Rejected }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Loan Attributes
  private Date startDate;
  private Date endDate;
  private LoanStatus loanStatus;

  //Loan Associations
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Visitor loanee;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;
  @OneToOne(cascade = CascadeType.PERSIST)
  private Artifact artifact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Loan(Date aStartDate, Date aEndDate, LoanStatus aLoanStatus, Visitor aLoanee, Artifact aArtifact, MyMuseum aMyMuseum)
  {
    startDate = aStartDate;
    endDate = aEndDate;
    loanStatus = aLoanStatus;
    boolean didAddLoanee = setLoanee(aLoanee);
    if (!didAddLoanee)
    {
      throw new RuntimeException("Unable to create loan due to loanee. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddArtifact = setArtifact(aArtifact);
    if (!didAddArtifact)
    {
      throw new RuntimeException("Unable to create loan due to artifact. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create loan due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Loan() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndDate(Date aEndDate)
  {
    boolean wasSet = false;
    endDate = aEndDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoanStatus(LoanStatus aLoanStatus)
  {
    boolean wasSet = false;
    loanStatus = aLoanStatus;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public Date getEndDate()
  {
    return endDate;
  }

  public LoanStatus getLoanStatus()
  {
    return loanStatus;
  }
  /* Code from template association_GetOne */
  public Visitor getLoanee()
  {
    return loanee;
  }
  /* Code from template association_GetOne */
  public Artifact getArtifact()
  {
    return artifact;
  }
  /* Code from template association_GetOne */
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setLoanee(Visitor aLoanee)
  {
    boolean wasSet = false;
    //Must provide loanee to loan
    if (aLoanee == null)
    {
      return wasSet;
    }

    //loanee already at maximum (3)
    if (aLoanee.numberOfLoans() >= Visitor.maximumNumberOfLoans())
    {
      return wasSet;
    }
    
    Visitor existingLoanee = loanee;
    loanee = aLoanee;
    if (existingLoanee != null && !existingLoanee.equals(aLoanee))
    {
      boolean didRemove = existingLoanee.removeLoan(this);
      if (!didRemove)
      {
        loanee = existingLoanee;
        return wasSet;
      }
    }
    loanee.addLoan(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setArtifact(Artifact aNewArtifact)
  {
    boolean wasSet = false;
    if (aNewArtifact == null)
    {
      //Unable to setArtifact to null, as loan must always be associated to a artifact
      return wasSet;
    }
    
    Loan existingLoan = aNewArtifact.getLoan();
    if (existingLoan != null && !equals(existingLoan))
    {
      //Unable to setArtifact, the current artifact already has a loan, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Artifact anOldArtifact = artifact;
    artifact = aNewArtifact;
    artifact.setLoan(this);

    if (anOldArtifact != null)
    {
      anOldArtifact.setLoan(null);
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
      existingMyMuseum.removeLoan(this);
    }
    myMuseum.addLoan(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Visitor placeholderLoanee = loanee;
    this.loanee = null;
    if(placeholderLoanee != null)
    {
      placeholderLoanee.removeLoan(this);
    }
    Artifact existingArtifact = artifact;
    artifact = null;
    if (existingArtifact != null)
    {
      existingArtifact.setLoan(null);
    }
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeLoan(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endDate" + "=" + (getEndDate() != null ? !getEndDate().equals(this)  ? getEndDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "loanStatus" + "=" + (getLoanStatus() != null ? !getLoanStatus().equals(this)  ? getLoanStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "loanee = "+(getLoanee()!=null?Integer.toHexString(System.identityHashCode(getLoanee())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "artifact = "+(getArtifact()!=null?Integer.toHexString(System.identityHashCode(getArtifact())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "myMuseum = "+(getMyMuseum()!=null?Integer.toHexString(System.identityHashCode(getMyMuseum())):"null");
  }
}