package com.mcgill.mymuseum.dto;

import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.Visitor;

import java.sql.Date;

/**
 * Data transfer Object for the Employee Class
 * @author Daniel Makhlin
 */

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

    /**
     * getter for the startTime
     * @return startTime of employee
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * getter for the endTime
     * @return endTime of employee
     */
    public String getEndTime(){
        return endTime;
    }

    /**
     * getter for the day date
     * @return the day date
     */
    public Date getDay() {
        return day;
    }

    /**
     * getter for the employee email
     * @return the employeeEmail
     */
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    /**
     * getter for the employee id
     * @return the employeeId
     */
    public Long getEmployeeId() {
        return employeeId;
    }
}
