package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;
import java.sql.Date;

// line 41 "model.ump"
// line 127 "model.ump"
public class MyMuseum
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MyMuseum Attributes
  private String openingHours;

  //MyMuseum Associations
  private President president;
  private List<WorkDay> schedule;
  private List<Employee> employees;
  private List<Visitor> visitors;
  private List<Loan> loans;
  private List<Artifact> artifacts;
  private List<MuseumPass> museumPasses;
  private StorageRoom storageRooms;
  private List<DisplayRoom> displayRooms;
  private Long museumId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MyMuseum(String aOpeningHours, President aPresident, StorageRoom aStorageRooms)
  {
    openingHours = aOpeningHours;
    if (aPresident == null || aPresident.getMyMuseum() != null)
    {
      throw new RuntimeException("Unable to create MyMuseum due to aPresident. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    president = aPresident;
    schedule = new ArrayList<WorkDay>();
    employees = new ArrayList<Employee>();
    visitors = new ArrayList<Visitor>();
    loans = new ArrayList<Loan>();
    artifacts = new ArrayList<Artifact>();
    museumPasses = new ArrayList<MuseumPass>();
    if (aStorageRooms == null || aStorageRooms.getMyMuseum() != null)
    {
      throw new RuntimeException("Unable to create MyMuseum due to aStorageRooms. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    storageRooms = aStorageRooms;
    displayRooms = new ArrayList<DisplayRoom>();
  }

  public MyMuseum(String aOpeningHours, String aEmailForPresident, String aPasswordForPresident, int aHourlyWageForPresident, int aOvertimeHourlyWageForPresident, MyMuseum aApplicationForPresident)
  {
    openingHours = aOpeningHours;
    president = new President(aEmailForPresident, aPasswordForPresident, aHourlyWageForPresident, aOvertimeHourlyWageForPresident, aApplicationForPresident, this);
    schedule = new ArrayList<WorkDay>();
    employees = new ArrayList<Employee>();
    visitors = new ArrayList<Visitor>();
    loans = new ArrayList<Loan>();
    artifacts = new ArrayList<Artifact>();
    museumPasses = new ArrayList<MuseumPass>();
    storageRooms = new StorageRoom(this);
    displayRooms = new ArrayList<DisplayRoom>();
  }

  public MyMuseum() {

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setOpeningHours(String aOpeningHours)
  {
    boolean wasSet = false;
    openingHours = aOpeningHours;
    wasSet = true;
    return wasSet;
  }

  public String getOpeningHours()
  {
    return openingHours;
  }
  /* Code from template association_GetOne */
  public President getPresident()
  {
    return president;
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
  /* Code from template association_GetMany */
  public Employee getEmployee(int index)
  {
    Employee aEmployee = employees.get(index);
    return aEmployee;
  }

  public List<Employee> getEmployees()
  {
    List<Employee> newEmployees = Collections.unmodifiableList(employees);
    return newEmployees;
  }

  public int numberOfEmployees()
  {
    int number = employees.size();
    return number;
  }

  public boolean hasEmployees()
  {
    boolean has = employees.size() > 0;
    return has;
  }

  public int indexOfEmployee(Employee aEmployee)
  {
    int index = employees.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_GetMany */
  public Visitor getVisitor(int index)
  {
    Visitor aVisitor = visitors.get(index);
    return aVisitor;
  }

  public List<Visitor> getVisitors()
  {
    List<Visitor> newVisitors = Collections.unmodifiableList(visitors);
    return newVisitors;
  }

  public int numberOfVisitors()
  {
    int number = visitors.size();
    return number;
  }

  public boolean hasVisitors()
  {
    boolean has = visitors.size() > 0;
    return has;
  }

  public int indexOfVisitor(Visitor aVisitor)
  {
    int index = visitors.indexOf(aVisitor);
    return index;
  }
  /* Code from template association_GetMany */
  public Loan getLoan(int index)
  {
    Loan aLoan = loans.get(index);
    return aLoan;
  }

  public List<Loan> getLoans()
  {
    List<Loan> newLoans = Collections.unmodifiableList(loans);
    return newLoans;
  }

  public int numberOfLoans()
  {
    int number = loans.size();
    return number;
  }

  public boolean hasLoans()
  {
    boolean has = loans.size() > 0;
    return has;
  }

  public int indexOfLoan(Loan aLoan)
  {
    int index = loans.indexOf(aLoan);
    return index;
  }
  /* Code from template association_GetMany */
  public Artifact getArtifact(int index)
  {
    Artifact aArtifact = artifacts.get(index);
    return aArtifact;
  }

  public List<Artifact> getArtifacts()
  {
    List<Artifact> newArtifacts = Collections.unmodifiableList(artifacts);
    return newArtifacts;
  }

  public int numberOfArtifacts()
  {
    int number = artifacts.size();
    return number;
  }

  public boolean hasArtifacts()
  {
    boolean has = artifacts.size() > 0;
    return has;
  }

  public int indexOfArtifact(Artifact aArtifact)
  {
    int index = artifacts.indexOf(aArtifact);
    return index;
  }
  /* Code from template association_GetMany */
  public MuseumPass getMuseumPass(int index)
  {
    MuseumPass aMuseumPass = museumPasses.get(index);
    return aMuseumPass;
  }

  public List<MuseumPass> getMuseumPasses()
  {
    List<MuseumPass> newMuseumPasses = Collections.unmodifiableList(museumPasses);
    return newMuseumPasses;
  }

  public int numberOfMuseumPasses()
  {
    int number = museumPasses.size();
    return number;
  }

  public boolean hasMuseumPasses()
  {
    boolean has = museumPasses.size() > 0;
    return has;
  }

  public int indexOfMuseumPass(MuseumPass aMuseumPass)
  {
    int index = museumPasses.indexOf(aMuseumPass);
    return index;
  }
  /* Code from template association_GetOne */
  public StorageRoom getStorageRooms()
  {
    return storageRooms;
  }
  /* Code from template association_GetMany */
  public DisplayRoom getDisplayRoom(int index)
  {
    DisplayRoom aDisplayRoom = displayRooms.get(index);
    return aDisplayRoom;
  }

  public List<DisplayRoom> getDisplayRooms()
  {
    List<DisplayRoom> newDisplayRooms = Collections.unmodifiableList(displayRooms);
    return newDisplayRooms;
  }

  public int numberOfDisplayRooms()
  {
    int number = displayRooms.size();
    return number;
  }

  public boolean hasDisplayRooms()
  {
    boolean has = displayRooms.size() > 0;
    return has;
  }

  public int indexOfDisplayRoom(DisplayRoom aDisplayRoom)
  {
    int index = displayRooms.indexOf(aDisplayRoom);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSchedule()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WorkDay addSchedule(String aStartTime, String aEndTime, Date aDay, Employee aEmployee)
  {
    return new WorkDay(aStartTime, aEndTime, aDay, aEmployee, this);
  }

  public boolean addSchedule(WorkDay aSchedule)
  {
    boolean wasAdded = false;
    if (schedule.contains(aSchedule)) { return false; }
    MyMuseum existingMyMuseum = aSchedule.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);
    if (isNewMyMuseum)
    {
      aSchedule.setMyMuseum(this);
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
    //Unable to remove aSchedule, as it must always have a myMuseum
    if (!this.equals(aSchedule.getMyMuseum()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Employee addEmployee(String aEmail, String aPassword, int aHourlyWage, int aOvertimeHourlyWage)
  {
    return new Employee(aEmail, aPassword, aHourlyWage, aOvertimeHourlyWage, this);
  }

  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    MyMuseum existingApplication = aEmployee.getApplication();
    boolean isNewApplication = existingApplication != null && !this.equals(existingApplication);
    if (isNewApplication)
    {
      aEmployee.setApplication(this);
    }
    else
    {
      employees.add(aEmployee);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployee(Employee aEmployee)
  {
    boolean wasRemoved = false;
    //Unable to remove aEmployee, as it must always have a application
    if (!this.equals(aEmployee.getApplication()))
    {
      employees.remove(aEmployee);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeeAt(Employee aEmployee, int index)
  {  
    boolean wasAdded = false;
    if(addEmployee(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeeAt(Employee aEmployee, int index)
  {
    boolean wasAdded = false;
    if(employees.contains(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeeAt(aEmployee, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVisitors()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Visitor addVisitor(String aEmail, String aPassword, boolean aIsBanned)
  {
    return new Visitor(aEmail, aPassword, aIsBanned, this);
  }

  public boolean addVisitor(Visitor aVisitor)
  {
    boolean wasAdded = false;
    if (visitors.contains(aVisitor)) { return false; }
    MyMuseum existingMyMuseum = aVisitor.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);
    if (isNewMyMuseum)
    {
      aVisitor.setMyMuseum(this);
    }
    else
    {
      visitors.add(aVisitor);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeVisitor(Visitor aVisitor)
  {
    boolean wasRemoved = false;
    //Unable to remove aVisitor, as it must always have a myMuseum
    if (!this.equals(aVisitor.getMyMuseum()))
    {
      visitors.remove(aVisitor);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addVisitorAt(Visitor aVisitor, int index)
  {  
    boolean wasAdded = false;
    if(addVisitor(aVisitor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitors()) { index = numberOfVisitors() - 1; }
      visitors.remove(aVisitor);
      visitors.add(index, aVisitor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveVisitorAt(Visitor aVisitor, int index)
  {
    boolean wasAdded = false;
    if(visitors.contains(aVisitor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfVisitors()) { index = numberOfVisitors() - 1; }
      visitors.remove(aVisitor);
      visitors.add(index, aVisitor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addVisitorAt(aVisitor, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(Date aStartDate, Date aEndDate, Loan.LoanStatus aLoanStatus, Visitor aLoanee, Artifact aArtifact)
  {
    return new Loan(aStartDate, aEndDate, aLoanStatus, aLoanee, this, aArtifact);
  }

  public boolean addLoan(Loan aLoan)
  {
    boolean wasAdded = false;
    if (loans.contains(aLoan)) { return false; }
    MyMuseum existingMyMuseum = aLoan.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);
    if (isNewMyMuseum)
    {
      aLoan.setMyMuseum(this);
    }
    else
    {
      loans.add(aLoan);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLoan(Loan aLoan)
  {
    boolean wasRemoved = false;
    //Unable to remove aLoan, as it must always have a myMuseum
    if (!this.equals(aLoan.getMyMuseum()))
    {
      loans.remove(aLoan);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLoanAt(Loan aLoan, int index)
  {  
    boolean wasAdded = false;
    if(addLoan(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLoanAt(Loan aLoan, int index)
  {
    boolean wasAdded = false;
    if(loans.contains(aLoan))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLoans()) { index = numberOfLoans() - 1; }
      loans.remove(aLoan);
      loans.add(index, aLoan);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLoanAt(aLoan, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtifacts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Artifact addArtifact()
  {
    return new Artifact(this);
  }

  public boolean addArtifact(Artifact aArtifact)
  {
    boolean wasAdded = false;
    if (artifacts.contains(aArtifact)) { return false; }
    MyMuseum existingMyMuseum = aArtifact.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);
    if (isNewMyMuseum)
    {
      aArtifact.setMyMuseum(this);
    }
    else
    {
      artifacts.add(aArtifact);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtifact(Artifact aArtifact)
  {
    boolean wasRemoved = false;
    //Unable to remove aArtifact, as it must always have a myMuseum
    if (!this.equals(aArtifact.getMyMuseum()))
    {
      artifacts.remove(aArtifact);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtifactAt(Artifact aArtifact, int index)
  {  
    boolean wasAdded = false;
    if(addArtifact(aArtifact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtifacts()) { index = numberOfArtifacts() - 1; }
      artifacts.remove(aArtifact);
      artifacts.add(index, aArtifact);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtifactAt(Artifact aArtifact, int index)
  {
    boolean wasAdded = false;
    if(artifacts.contains(aArtifact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtifacts()) { index = numberOfArtifacts() - 1; }
      artifacts.remove(aArtifact);
      artifacts.add(index, aArtifact);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtifactAt(aArtifact, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMuseumPasses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MuseumPass addMuseumPass(int aPassCost, Date aPassDate, Visitor aOwner)
  {
    return new MuseumPass(aPassCost, aPassDate, aOwner, this);
  }

  public boolean addMuseumPass(MuseumPass aMuseumPass)
  {
    boolean wasAdded = false;
    if (museumPasses.contains(aMuseumPass)) { return false; }
    MyMuseum existingMyMuseum = aMuseumPass.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);
    if (isNewMyMuseum)
    {
      aMuseumPass.setMyMuseum(this);
    }
    else
    {
      museumPasses.add(aMuseumPass);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMuseumPass(MuseumPass aMuseumPass)
  {
    boolean wasRemoved = false;
    //Unable to remove aMuseumPass, as it must always have a myMuseum
    if (!this.equals(aMuseumPass.getMyMuseum()))
    {
      museumPasses.remove(aMuseumPass);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMuseumPassAt(MuseumPass aMuseumPass, int index)
  {  
    boolean wasAdded = false;
    if(addMuseumPass(aMuseumPass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMuseumPasses()) { index = numberOfMuseumPasses() - 1; }
      museumPasses.remove(aMuseumPass);
      museumPasses.add(index, aMuseumPass);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMuseumPassAt(MuseumPass aMuseumPass, int index)
  {
    boolean wasAdded = false;
    if(museumPasses.contains(aMuseumPass))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMuseumPasses()) { index = numberOfMuseumPasses() - 1; }
      museumPasses.remove(aMuseumPass);
      museumPasses.add(index, aMuseumPass);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMuseumPassAt(aMuseumPass, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfDisplayRoomsValid()
  {
    boolean isValid = numberOfDisplayRooms() >= minimumNumberOfDisplayRooms() && numberOfDisplayRooms() <= maximumNumberOfDisplayRooms();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDisplayRooms()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDisplayRooms()
  {
    return 10;
  }
  /* Code from template association_AddMNToOnlyOne */
  public DisplayRoom addDisplayRoom(int aRoomCapacity)
  {
    if (numberOfDisplayRooms() >= maximumNumberOfDisplayRooms())
    {
      return null;
    }
    else
    {
      return new DisplayRoom(aRoomCapacity, this);
    }
  }

  public boolean addDisplayRoom(DisplayRoom aDisplayRoom)
  {
    boolean wasAdded = false;
    if (displayRooms.contains(aDisplayRoom)) { return false; }
    if (numberOfDisplayRooms() >= maximumNumberOfDisplayRooms())
    {
      return wasAdded;
    }

    MyMuseum existingMyMuseum = aDisplayRoom.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);

    if (isNewMyMuseum && existingMyMuseum.numberOfDisplayRooms() <= minimumNumberOfDisplayRooms())
    {
      return wasAdded;
    }

    if (isNewMyMuseum)
    {
      aDisplayRoom.setMyMuseum(this);
    }
    else
    {
      displayRooms.add(aDisplayRoom);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDisplayRoom(DisplayRoom aDisplayRoom)
  {
    boolean wasRemoved = false;
    //Unable to remove aDisplayRoom, as it must always have a myMuseum
    if (this.equals(aDisplayRoom.getMyMuseum()))
    {
      return wasRemoved;
    }

    //myMuseum already at minimum (1)
    if (numberOfDisplayRooms() <= minimumNumberOfDisplayRooms())
    {
      return wasRemoved;
    }
    displayRooms.remove(aDisplayRoom);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDisplayRoomAt(DisplayRoom aDisplayRoom, int index)
  {  
    boolean wasAdded = false;
    if(addDisplayRoom(aDisplayRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDisplayRooms()) { index = numberOfDisplayRooms() - 1; }
      displayRooms.remove(aDisplayRoom);
      displayRooms.add(index, aDisplayRoom);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDisplayRoomAt(DisplayRoom aDisplayRoom, int index)
  {
    boolean wasAdded = false;
    if(displayRooms.contains(aDisplayRoom))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDisplayRooms()) { index = numberOfDisplayRooms() - 1; }
      displayRooms.remove(aDisplayRoom);
      displayRooms.add(index, aDisplayRoom);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDisplayRoomAt(aDisplayRoom, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    President existingPresident = president;
    president = null;
    if (existingPresident != null)
    {
      existingPresident.delete();
    }
    while (schedule.size() > 0)
    {
      WorkDay aSchedule = schedule.get(schedule.size() - 1);
      aSchedule.delete();
      schedule.remove(aSchedule);
    }
    
    while (employees.size() > 0)
    {
      Employee aEmployee = employees.get(employees.size() - 1);
      aEmployee.delete();
      employees.remove(aEmployee);
    }
    
    while (visitors.size() > 0)
    {
      Visitor aVisitor = visitors.get(visitors.size() - 1);
      aVisitor.delete();
      visitors.remove(aVisitor);
    }
    
    while (loans.size() > 0)
    {
      Loan aLoan = loans.get(loans.size() - 1);
      aLoan.delete();
      loans.remove(aLoan);
    }
    
    while (artifacts.size() > 0)
    {
      Artifact aArtifact = artifacts.get(artifacts.size() - 1);
      aArtifact.delete();
      artifacts.remove(aArtifact);
    }
    
    while (museumPasses.size() > 0)
    {
      MuseumPass aMuseumPass = museumPasses.get(museumPasses.size() - 1);
      aMuseumPass.delete();
      museumPasses.remove(aMuseumPass);
    }
    
    StorageRoom existingStorageRooms = storageRooms;
    storageRooms = null;
    if (existingStorageRooms != null)
    {
      existingStorageRooms.delete();
    }
    while (displayRooms.size() > 0)
    {
      DisplayRoom aDisplayRoom = displayRooms.get(displayRooms.size() - 1);
      aDisplayRoom.delete();
      displayRooms.remove(aDisplayRoom);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "openingHours" + ":" + getOpeningHours()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "president = "+(getPresident()!=null?Integer.toHexString(System.identityHashCode(getPresident())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "storageRooms = "+(getStorageRooms()!=null?Integer.toHexString(System.identityHashCode(getStorageRooms())):"null");
  }

  public void setMuseumId(Long museumId) {
    this.museumId = museumId;
  }

  @Id
  public Long getMuseumId() {
    return museumId;
  }
}