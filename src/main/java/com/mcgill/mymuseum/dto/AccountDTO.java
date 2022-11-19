package com.mcgill.mymuseum.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    public String email=null;
    private String password=null;
    public String accountType=null;
    public Double hourlyWage=null;
    public Double overTimeHourlyWage=null;
    public AccountDTO(){}

    public AccountDTO(String email, String password, String accountType){
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }
    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
