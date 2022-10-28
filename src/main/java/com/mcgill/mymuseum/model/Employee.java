package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.*;
import java.util.*;
import java.sql.Date;

// line 33 "model.ump"
// line 119 "model.ump"
@Entity
public class Employee extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private int hourlyWage;
  private int overtimeHourlyWage;

  //Employee Associations
  @OneToMany(mappedBy = "employee")
  private List<WorkDay> schedule;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum application;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aEmail, String aPassword, int aHourlyWage, int aOvertimeHourlyWage, MyMuseum aApplication)
  {
    super(aEmail, aPassword);
    hourlyWage = aHourlyWage;
    overtimeHourlyWage = aOvertimeHourlyWage;
    schedule = new ArrayList<WorkDay>();
    this.application = aApplication;
    /*boolean didAddApplication = setApplication(aApplication);
    if (!didAddApplication)
    {
      throw new RuntimeException("Unable to create employee due to application. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }*/
  }

  public Employee() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHourlyWage(int aHourlyWage)
  {
    boolean wasSet = false;
    hourlyWage = aHourlyWage;
    wasSet = true;
    return wasSet;
  }

  public boolean setOvertimeHourlyWage(int aOvertimeHourlyWage)
  {
    boolean wasSet = false;
    overtimeHourlyWage = aOvertimeHourlyWage;
    wasSet = true;
    return wasSet;
  }

  public int getHourlyWage()
  {
    return hourlyWage;
  }

  public int getOvertimeHourlyWage()
  {
    return overtimeHourlyWage;
  }
  /* Code from template association_GetMany */
  public WorkDay getSchedule(int index)
  {
    WorkDay aSchedule = schedule.get(index);
    return aSchedule;
  }

  public List<WorkDay> getSchedule()
  {
    List<WorkDay> newSchedule = Collections.unmodifiableList(schedule);
    return newSchedule;
  }

  public int numberOfSchedule()
  {
    int number = schedule.size();
    return number;
  }

  public boolean hasSchedule()
  {
    boolean has = schedule.size() > 0;
    return has;
  }

  public int indexOfSchedule(WorkDay aSchedule)
  {
    int index = schedule.indexOf(aSchedule);
    return index;
  }
  /* Code from template association_GetOne */
  public MyMuseum getApplication()
  {
    return application;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSchedule()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WorkDay addSchedule(String aStartTime, String aEndTime, Date aDay, MyMuseum aMyMuseum)
  {
    return new WorkDay(aStartTime, aEndTime, aDay, this, aMyMuseum);
  }

  public boolean addSchedule(WorkDay aSchedule)
  {
    boolean wasAdded = false;
    if (schedule.contains(aSchedule)) { return false; }
    Employee existingEmployee = aSchedule.getEmployee();
    boolean isNewEmployee = existingEmployee != null && !this.equals(existingEmployee);
    if (isNewEmployee)
    {
      aSchedule.setEmployee(this);
    }
    else
    {
      schedule.add(aSchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSchedule(WorkDay aSchedule)
  {
    boolean wasRemoved = false;
    //Unable to remove aSchedule, as it must always have a employee
    if (!this.equals(aSchedule.getEmployee()))
    {
      schedule.remove(aSchedule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addScheduleAt(WorkDay aSchedule, int index)
  {  
    boolean wasAdded = false;
    if(addSchedule(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedule()) { index = numberOfSchedule() - 1; }
      schedule.remove(aSchedule);
      schedule.add(index, aSchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScheduleAt(WorkDay aSchedule, int index)
  {
    boolean wasAdded = false;
    if(schedule.contains(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedule()) { index = numberOfSchedule() - 1; }
      schedule.remove(aSchedule);
      schedule.add(index, aSchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScheduleAt(aSchedule, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setApplication(MyMuseum aApplication)
  {
    boolean wasSet = false;
    if (aApplication == null)
    {
      return wasSet;
    }

    MyMuseum existingApplication = application;
    application = aApplication;
    if (existingApplication != null && !existingApplication.equals(aApplication))
    {
      existingApplication.removeEmployee(this);
    }
    application.addEmployee(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=schedule.size(); i > 0; i--)
    {
      WorkDay aSchedule = schedule.get(i - 1);
      aSchedule.delete();
    }
    MyMuseum placeholderApplication = application;
    this.application = null;
    if(placeholderApplication != null)
    {
      placeholderApplication.removeEmployee(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "hourlyWage" + ":" + getHourlyWage()+ "," +
            "overtimeHourlyWage" + ":" + getOvertimeHourlyWage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "application = "+(getApplication()!=null?Integer.toHexString(System.identityHashCode(getApplication())):"null");
  }
}