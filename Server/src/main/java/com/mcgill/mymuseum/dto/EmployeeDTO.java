package com.mcgill.mymuseum.dto;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data transfer Object for the Employee Class
 * @author Androw Abd El Malak
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    public String email=null;
    private String password=null;
    public Double hourlyWage=null;
    public Double overTimeHourlyWage=null;
    public EmployeeDTO(){}

    public EmployeeDTO(String email, String password, Double hourlyWage, Double overTimeHourlyWage){
        this.email = email;
        this.password = password;
        this.hourlyWage = hourlyWage;
        this.overTimeHourlyWage = overTimeHourlyWage;
    }

    /**
     * getter for employeeEmail
     * @return email of employee
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * getter for employeePassword
     * @return password of employee
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * setter for employeeEmail
     * @param email of employee
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * setter for employeePassword
     * @param password of employee
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * setter for employeeHourlyWage
     * @param hourlyWage of employee
     */
    public void setHourlyWage(Double hourlyWage){this.hourlyWage = hourlyWage;};

    /**
     * setter for employeeOverTimeHourlyWage
     * @param overTimeHourlyWage of employee
     */
    public void setOverTimeHourlyWage(Double overTimeHourlyWage){this.overTimeHourlyWage = overTimeHourlyWage;};

    /**
     * getter for employeeHourlyWage
     * @return hourlyWage for employee
     */
    public Double getHourlyWage(){
        return this.hourlyWage;
    }

    /**
     * getter for overTimeHourlyWage
     * @return overTimeHourlyWage for employee
     */
    public Double getOverTimeHourlyWage(){
        return this.overTimeHourlyWage;
    }

}
