package com.mcgill.mymuseum.dto;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO {
    private String firstName =null;
    private String lastName = null;
    private String email=null;
    private String password=null;
    private String accountType=null;
    private Double hourlyWage=null;
    private Long id=null;
    private Double overTimeHourlyWage=null;
    public AccountDTO(){}

    /**
     * Constructor of accountDTO
     * @param email
     * @param password
     * @param accountType
     */
    public AccountDTO(String email, String password, String accountType){
        this.email = email;
        this.password = password;
        this.accountType = accountType;
    }

    public String getFirstName() {return this.firstName;}

    public String  getLastName() {return this.lastName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPassword(){
        return this.password;
    }

    public Long getId(){
        return this.id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() { return this.email; }

    public void setEmail(String email){ this.email = email;}

    public String getAccountType() { return this.accountType; }

    public void setAccountType(String accountType) { this.accountType = accountType;}

    public Double getHourlyWage() { return this.hourlyWage;}

    public void setHourlyWage(Double hourlyWage) { this.hourlyWage = hourlyWage;}

    public Double getOverTimeHourlyWage() { return this.overTimeHourlyWage; }

    public void setOverTimeHourlyWage(Double overTimeHourlyWage) { this.overTimeHourlyWage = overTimeHourlyWage; }
}
