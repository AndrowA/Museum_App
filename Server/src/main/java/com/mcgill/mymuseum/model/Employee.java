package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.*;

// line 14 "model.ump"
// line 106 "model.ump"
@Entity
public class Employee extends Account
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private double hourlyWage;
  private double overTimeHourlyWage;

  //Employee Associations
  @OneToMany(mappedBy = "employee")
  private List<WorkDay> schedule;
  @ManyToOne(cascade = CascadeType.PERSIST)
  private MyMuseum myMuseum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aEmail, String aPassword, double aHourlyWage, double aOverTimeHourlyWage, MyMuseum aMyMuseum)
  {
    super(aEmail, aPassword);
    hourlyWage = aHourlyWage;
    overTimeHourlyWage = aOverTimeHourlyWage;
    schedule = new ArrayList<WorkDay>();
    boolean didAddMyMuseum = setMyMuseum(aMyMuseum);
    if (!didAddMyMuseum)
    {
      throw new RuntimeException("Unable to create employee due to myMuseum. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Employee() {

  }
  //------------------------
  // INTERFACE
  //------------------------

  public boolean setHourlyWage(double aHourlyWage)
  {
    boolean wasSet = false;
    hourlyWage = aHourlyWage;
    wasSet = true;
    return wasSet;
  }

  public boolean setOverTimeHourlyWage(double aOverTimeHourlyWage)
  {
    boolean wasSet = false;
    overTimeHourlyWage = aOverTimeHourlyWage;
    wasSet = true;
    return wasSet;
  }

  public double getHourlyWage()
  {
    return hourlyWage;
  }

  public double getOverTimeHourlyWage()
  {
    return overTimeHourlyWage;
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
  public MyMuseum getMyMuseum()
  {
    return myMuseum;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSchedule()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addSchedule(WorkDay aSchedule)
  {
    boolean wasAdded = false;
    if (schedule.contains(aSchedule)) { return false; }
    Employee existingEmployee = aSchedule.getEmployee();
    if (existingEmployee == null)
    {
      aSchedule.setEmployee(this);
    }
    else if (!this.equals(existingEmployee))
    {
      existingEmployee.removeSchedule(aSchedule);
      addSchedule(aSchedule);
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
    if (schedule.contains(aSchedule))
    {
      schedule.remove(aSchedule);
      aSchedule.setEmployee(null);
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
      existingMyMuseum.removeEmployee(this);
    }
    myMuseum.addEmployee(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while( !schedule.isEmpty() )
    {
      schedule.get(0).setEmployee(null);
    }
    MyMuseum placeholderMyMuseum = myMuseum;
    this.myMuseum = null;
    if(placeholderMyMuseum != null)
    {
      placeholderMyMuseum.removeEmployee(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "hourlyWage" + ":" + getHourlyWage()+ "," +
            "overTimeHourlyWage" + ":" + getOverTimeHourlyWage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "myMuseum = "+(getMyMuseum()!=null?Integer.toHexString(System.identityHashCode(getMyMuseum())):"null");
  }
}



