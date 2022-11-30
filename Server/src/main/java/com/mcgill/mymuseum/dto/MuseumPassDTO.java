package com.mcgill.mymuseum.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.Visitor;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MuseumPassDTO {
    public void setaPassDate(Date aPassDate) {
        this.aPassDate = aPassDate;
    }

    private Date aPassDate;

    private String email;
    private MyMuseum aMyMuseum;
    private Long passId;
    private Integer passCost;



    private String visitorEmail;

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    /**
     * Constructor of museum pass DTO
     * @param passId
     * @param passCost
     * @param aPassDate
     * @param email
     * @param aMyMuseum
     */
    public MuseumPassDTO(Long passId, Integer passCost, Date aPassDate, String email, MyMuseum aMyMuseum) {
        this.aPassDate = aPassDate;
        this.email = email;
        this.aMyMuseum = aMyMuseum;
        this.passId = passId;
        this.passCost = passCost;
    }
    public MuseumPassDTO(){

    }
    public Date getaPassDate() {
        return aPassDate;
    }
    public String getEmail() {return email;}
    public Long getPassId() {return passId;}

    public String getVisitorEmail() {return this.visitorEmail;}
    public void setPassCost(Integer passCost) {
        this.passCost = passCost;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassId(Long passId) { this.passId = passId;}
}
