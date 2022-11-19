package com.mcgill.mymuseum.model;

/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// line 2 "model.ump"
// line 98 "model.ump"
@Entity
public abstract class Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Account Attributes
  @Id
  @GeneratedValue
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Long accountId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Account(String aFirstName, String aLastName, String aEmail, String aPassword)
  {
    firstName = aFirstName;
    lastName = aLastName;
    email = aEmail;
    password = aPassword;
  }

  public Account() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

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

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "," +
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