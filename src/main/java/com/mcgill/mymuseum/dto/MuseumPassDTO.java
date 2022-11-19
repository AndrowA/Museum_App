package com.mcgill.mymuseum.dto;
import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.Visitor;

import java.util.Date;

public class MuseumPassDTO {
    private Date aPassDate;
    private Visitor aOwner;
    private MyMuseum aMyMuseum;
    private Long passId;
    private Integer passCost;

    public MuseumPassDTO(Long passId, Integer passCost, Date aPassDate, Visitor aOwner, MyMuseum aMyMuseum) {
        this.aPassDate = aPassDate;
        this.aOwner = aOwner;
        this.aMyMuseum = aMyMuseum;
        this.passId = passId;
        this.passCost = passCost;
    }
    public Date getaPassDate() {
        return aPassDate;
    }
    public Integer getPassCost() {
        return passCost;
    }
    public String getEmail() {return aOwner.getEmail();}
    public Long getPassId() {return passId;}
}
