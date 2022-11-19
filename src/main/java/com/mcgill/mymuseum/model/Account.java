package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// line 2 "model.ump"
// line 96 "model.ump"
@Entity
public abstract class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  @Id
  @GeneratedValue
  private Long accountId;
  private String email;
  private String password;
  private String firstName;
  private String lastName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String aEmail, String aPassword)
  {
    email = aEmail;
    password = aPassword;
  }

  public Account() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
  public void setAccountId(Long accountId) {
    this.accountId = accountId;
  }

  public Long getAccountId() {
    return accountId;
  }
}