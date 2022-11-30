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
    private Visitor aOwner;
    private MyMuseum aMyMuseum;
    private Long passId;
    private Integer passCost;

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    private String visitorEmail;

    /**
     * Constructor of museum pass DTO
     * @param passId
     * @param passCost
     * @param aPassDate
     * @param aOwner
     * @param aMyMuseum
     */
    public MuseumPassDTO(Long passId, Integer passCost, Date aPassDate, Visitor aOwner, MyMuseum aMyMuseum) {
        this.aPassDate = aPassDate;
        this.aOwner = aOwner;
        this.aMyMuseum = aMyMuseum;
        this.passId = passId;
        this.passCost = passCost;
    }
    public MuseumPassDTO(){

    }
    public Date getaPassDate() {
        return aPassDate;
    }
    public Integer getPassCost() {
        return passCost;
    }
    public String getEmail() {return aOwner.getEmail();}
    public Long getPassId() {return passId;}

    public Integer setPassCost(Integer passCost) {
        return passCost;
    }
    public String setEmail(String email) {return aOwner.getEmail();}
    public Long setPassId(Long passId) {return passId;}
}
