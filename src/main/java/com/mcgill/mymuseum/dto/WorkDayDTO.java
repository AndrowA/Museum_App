package com.mcgill.mymuseum.dto;

import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.Visitor;

import java.sql.Date;

public class WorkDayDTO {

    private String startTime;
    private String endTime;
    private Date day;
    private String employeeEmail;
    private Long employeeId;

    public WorkDayDTO(String startTime, String endTime, Date day, String employeeEmail, Long employeeId) {
        this.startTime=startTime;
        this.endTime = endTime;
        this.day = day;
        this.employeeEmail = employeeEmail;
        this.employeeId = employeeId;
    }

    public String getStartTime() {
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
    public Date getDay() {
        return day;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
}
