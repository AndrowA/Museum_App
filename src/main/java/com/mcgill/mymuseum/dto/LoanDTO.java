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
    private String aLoaneeName;
    private String aArtifactName;
    private Long aArtifactId;
    private String aMyMuseumName;

    public LoanDTO(Date aStartDate, Date aEndDate, Loan.LoanStatus aLoanStatus, String aLoaneeName, String aArtifactName, Long aArtifactId, String aMyMuseumName) {
        this.aStartDate = aStartDate;
        this.aEndDate = aEndDate;
        this.aLoaneeName = aLoaneeName;
        this.aLoanStatus = aLoanStatus;
        this.aArtifactName = aArtifactName;
        this.aArtifactId = aArtifactId;
        this.aMyMuseumName = aMyMuseumName;
    }

    public Long getaArtifactId() {
        return aArtifactId;
    }

    public String getaLoaneeName() {
        return aLoaneeName;
    }

    public String getaArtifactName() {
        return aArtifactName;
    }

    public String getaMyMuseumName() {
        return aMyMuseumName;
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
}
