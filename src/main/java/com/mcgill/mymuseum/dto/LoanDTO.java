package com.mcgill.mymuseum.dto;

import com.mcgill.mymuseum.model.Loan;

import java.sql.Date;
/**
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

    /**
     * getter for artifactId
     * @return artifactId
     */
    public Long getaArtifactId() {
        return aArtifactId;
    }
    /**
     * getter for loaneeName
     * @return loaneeName
     */
    public String getaLoaneeName() {
        return aLoaneeName;
    }
    /**
     * getter for artifactName
     * @return artifactName
     */
    public String getaArtifactName() {
        return aArtifactName;
    }
    /**
     * getter for MyMuseumName
     * @return MyMuseumName
     */
    public String getaMyMuseumName() {
        return aMyMuseumName;
    }
    /**
     * getter for endDate
     * @return endDate
     */
    public Date getaEndDate() {
        return aEndDate;
    }
    /**
     * getter for startDate
     * @return startDate
     */
    public Date getaStartDate() {
        return aStartDate;
    }
    /**
     * getter for loanStatus
     * @return loanStatus
     */
    public Loan.LoanStatus getaLoanStatus() {
        return aLoanStatus;
    }
}
