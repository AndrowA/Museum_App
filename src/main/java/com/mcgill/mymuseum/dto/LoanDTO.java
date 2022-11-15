package com.mcgill.mymuseum.dto;

import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.Visitor;

import java.sql.Date;
/*
    Data Transfer Object for the Loan class.
 */
public class LoanDTO {
    private Date aStartDate;
    private Date aEndDate;
    private Loan.LoanStatus aLoanStatus;
    private VisitorDTO aLoanee;
    private ArtifactDTO aArtifact;
    private MyMuseumDTO aMyMuseum;

    public LoanDTO(Date aStartDate, Date aEndDate, Loan.LoanStatus aLoanStatus, Visitor aLoanee, Artifact aArtifact, MyMuseum aMyMuseum) {
        this.aStartDate = aStartDate;
        this.aEndDate = aEndDate;
        this.aLoanee = aLoanee;
        this.aLoanStatus = aLoanStatus;
        this.aArtifact = aArtifact;
        this.aMyMuseum = aMyMuseum;
    }

    public Date getaEndDate() {
        return aEndDate;
    }

    public Date getaStartDate() {
        return aStartDate;
    }

    public Loan.LoanStatus getaLoanStatus() {
        return aLoanStatus;
    }

    public VisitorDTO getaLoanee() {
        return aLoanee;
    }

    public ArtifactDTO getaArtifact() {
        return aArtifact;
    }

    public MyMuseumDTO getaMyMuseum() {
        return aMyMuseum;
    }
}
