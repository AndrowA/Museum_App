package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.*;
import java.sql.Date;

// line 27 "model.ump"
// line 121 "model.ump"
@Entity
public class WorkDay
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WorkDay Attributes
  private String startTime;
  private String endTime;
  private Date day;

  //WorkDay Associations
  @ManyToOne(cascade = CascadeType.MERGE)
  private Employee employee;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum museum;
  @Id
  @GeneratedValue
  private Long id;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WorkDay(String aStartTime, String aEndTime, Date aDay, MyMuseum aMyMuseum, MyMuseum aMuseum)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    day = aDay;
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create workday due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMuseum = setMuseum(aMuseum);
    if (!didAddMuseum)
    {
      throw new RuntimeException("Unable to create openingHour due to museum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public WorkDay() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(String aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(String aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setDay(Date aDay)
  {
    boolean wasSet = false;
    day = aDay;
    wasSet = true;
    return wasSet;
  }

  public String getStartTime()
  {
    return startTime;
  }

  public String getEndTime()
  {
    return endTime;
  }

  public Date getDay()
  {
    return day;
  }
  /* Code from template association_GetOne */
  public Employee getEmployee()
  {
    return employee;
  }

  public boolean hasEmployee()
  {
    boolean has = employee != null;
    return has;
  }
  /* Code from template association_GetOne */
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }
  /* Code from template association_GetOne */
  public MyMuseum getMuseum()
  {
    return museum;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setEmployee(Employee aEmployee)
  {
    boolean wasSet = false;
    Employee existingEmployee = employee;
    employee = aEmployee;
    if (existingEmployee != null && !existingEmployee.equals(aEmployee))
    {
      existingEmployee.removeSchedule(this);
    }
    if (aEmployee != null)
    {
      aEmployee.addSchedule(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMyMuseum(MyMuseum aMyMuseum)
  {
    boolean wasSet = false;
    if (aMyMuseum == null)
    {
      return wasSet;
    }

    MyMuseum existingMyMuseum = myMuseum;
    myMuseum = aMyMuseum;
    if (existingMyMuseum != null && !existingMyMuseum.equals(aMyMuseum))
    {
      existingMyMuseum.removeWorkday(this);
    }
    myMuseum.addWorkday(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMuseum(MyMuseum aMuseum)
  {
    boolean wasSet = false;
    if (aMuseum == null)
    {
      return wasSet;
    }

    MyMuseum existingMuseum = museum;
    museum = aMuseum;
    if (existingMuseum != null && !existingMuseum.equals(aMuseum))
    {
      existingMuseum.removeOpeningHour(this);
    }
    museum.addOpeningHour(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (employee != null)
    {
      Employee placeholderEmployee = employee;
      this.employee = null;
      placeholderEmployee.removeSchedule(this);
    }
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeWorkday(this);
    }
    MyMuseum placeholderMuseum = museum;
    this.museum = null;
    if(placeholderMuseum != null)
    {
      placeholderMuseum.removeOpeningHour(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "startTime" + ":" + getStartTime()+ "," +
            "endTime" + ":" + getEndTime()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "day" + "=" + (getDay() != null ? !getDay().equals(this)  ? getDay().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "myMuseum = "+(getMyMuseum()!=null?Integer.toHexString(System.identityHashCode(getMyMuseum())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "museum = "+(getMuseum()!=null?Integer.toHexString(System.identityHashCode(getMuseum())):"null");
  }
  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}