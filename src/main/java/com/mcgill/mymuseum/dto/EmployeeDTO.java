package com.mcgill.mymuseum.dto;
import com.fasterxml.jackson.annotation.JsonInclude;

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

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHourlyWage(Double hourlyWage){this.hourlyWage = hourlyWage;};

    public void setOverTimeHourlyWage(Double overTimeHourlyWage){this.overTimeHourlyWage = overTimeHourlyWage;};

    public Double getHourlyWage(){
        return this.hourlyWage;
    }

    public Double getOverTimeHourlyWage(){
        return this.overTimeHourlyWage;
    }

}
