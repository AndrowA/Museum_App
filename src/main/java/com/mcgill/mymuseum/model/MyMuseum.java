package com.mcgill.mymuseum.model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/


import javax.persistence.*;
import java.util.*;
import java.sql.Date;

// line 75 "model.ump"
// line 171 "model.ump"
@Entity
public class MyMuseum
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MyMuseum Attributes
  private String pricePerPass;
  private String address;

  //MyMuseum Associations
  @OneToOne(mappedBy = "myMuseum")
  private President president;
  @OneToMany(mappedBy = "museum")
  private List<WorkDay> openingHours;
  @OneToMany(mappedBy = "myMuseum")
  private List<Employee> employees;
  @OneToMany(mappedBy = "myMuseum")
  private List<Visitor> visitors;
  @OneToMany(mappedBy = "myMuseum")
  private List<Loan> loans;
  @OneToMany(mappedBy = "myMuseum")
  private List<Artifact> artifacts;
  @OneToMany(mappedBy = "myMuseum")
  private List<MuseumPass> museumPasses;
  @OneToOne(mappedBy = "myMuseum")
  private StorageRoom storageRoom;
  @OneToMany(mappedBy = "myMuseum")
  private List<WorkDay> workdays;
  @OneToMany(mappedBy = "myMuseum")
  private List<DisplayRoom> displayRooms;
  @Id
  @GeneratedValue
  private Long museumId;
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MyMuseum(String aPricePerPass, String aAddress, President aPresident, StorageRoom aStorageRoom)
  {
    pricePerPass = aPricePerPass;
    address = aAddress;
    if (aPresident == null || aPresident.getMyMuseum() != null)
    {
      throw new RuntimeException("Unable to create MyMuseum due to aPresident. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    president = aPresident;
    artifacts = new ArrayList<Artifact>();
    loans = new ArrayList<Loan>();
    museumPasses = new ArrayList<MuseumPass>();
    visitors = new ArrayList<Visitor>();
    workdays = new ArrayList<WorkDay>();
    if (aStorageRoom == null || aStorageRoom.getMyMuseum() != null)
    {
      throw new RuntimeException("Unable to create MyMuseum due to aStorageRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    storageRoom = aStorageRoom;
    displayRooms = new ArrayList<DisplayRoom>();
    employees = new ArrayList<Employee>();
    openingHours = new ArrayList<WorkDay>();
  }

  public MyMuseum(String aPricePerPass, String aAddress, String aEmailForPresident, String aPasswordForPresident, double aHourlyWageForPresident, double aOverTimeHourlyWageForPresident, String aNameForStorageRoom)
  {
    pricePerPass = aPricePerPass;
    address = aAddress;
    president = new President(aEmailForPresident, aPasswordForPresident, aHourlyWageForPresident, aOverTimeHourlyWageForPresident, this);
    artifacts = new ArrayList<Artifact>();
    loans = new ArrayList<Loan>();
    museumPasses = new ArrayList<MuseumPass>();
    visitors = new ArrayList<Visitor>();
    workdays = new ArrayList<WorkDay>();
    storageRoom = new StorageRoom(aNameForStorageRoom, this);
    displayRooms = new ArrayList<DisplayRoom>();
    employees = new ArrayList<Employee>();
    openingHours = new ArrayList<WorkDay>();
  }

  public MyMuseum() {
    artifacts = new ArrayList<Artifact>();
    loans = new ArrayList<Loan>();
    museumPasses = new ArrayList<MuseumPass>();
    visitors = new ArrayList<Visitor>();
    workdays = new ArrayList<WorkDay>();
    displayRooms = new ArrayList<DisplayRoom>();
    employees = new ArrayList<Employee>();
    openingHours = new ArrayList<WorkDay>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPricePerPass(String aPricePerPass)
  {
    boolean wasSet = false;
    pricePerPass = aPricePerPass;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public String getPricePerPass()
  {
    return pricePerPass;
  }

  public String getAddress()
  {
    return address;
  }
  /* Code from template association_GetOne */
  public President getPresident()
  {
    return president;
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
  public WorkDay getWorkday(int index)
  {
    WorkDay aWorkday = workdays.get(index);
    return aWorkday;
  }

  public List<WorkDay> getWorkdays()
  {
    List<WorkDay> newWorkdays = Collections.unmodifiableList(workdays);
    return newWorkdays;
  }

  public int numberOfWorkdays()
  {
    int number = workdays.size();
    return number;
  }

  public boolean hasWorkdays()
  {
    boolean has = workdays.size() > 0;
    return has;
  }

  public int indexOfWorkday(WorkDay aWorkday)
  {
    int index = workdays.indexOf(aWorkday);
    return index;
  }
  /* Code from template association_GetOne */
  public StorageRoom getStorageRoom()
  {
    return storageRoom;
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
  public WorkDay getOpeningHour(int index)
  {
    WorkDay aOpeningHour = openingHours.get(index);
    return aOpeningHour;
  }

  public List<WorkDay> getOpeningHours()
  {
    List<WorkDay> newOpeningHours = Collections.unmodifiableList(openingHours);
    return newOpeningHours;
  }

  public int numberOfOpeningHours()
  {
    int number = openingHours.size();
    return number;
  }

  public boolean hasOpeningHours()
  {
    boolean has = openingHours.size() > 0;
    return has;
  }

  public int indexOfOpeningHour(WorkDay aOpeningHour)
  {
    int index = openingHours.indexOf(aOpeningHour);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtifacts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Artifact addArtifact(String aName, String aDescription, String aUrl, Room aRoom)
  {
    return new Artifact(aName, aDescription, aUrl, aRoom, this);
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
  public static int minimumNumberOfLoans()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Loan addLoan(Date aStartDate, Date aEndDate, Loan.LoanStatus aLoanStatus, Visitor aLoanee, Artifact aArtifact)
  {
    return new Loan(aStartDate, aEndDate, aLoanStatus, aLoanee, aArtifact, this);
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
  public static int minimumNumberOfMuseumPasses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MuseumPass addMuseumPass(Date aPassDate, Visitor aOwner)
  {
    return new MuseumPass(aPassDate, aOwner, this);
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfVisitors()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Visitor addVisitor(String aEmail, String aPassword)
  {
    return new Visitor(aEmail, aPassword, this);
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
  public static int minimumNumberOfWorkdays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WorkDay addWorkday(String aStartTime, String aEndTime, Date aDay, MyMuseum aMuseum)
  {
    return new WorkDay(aStartTime, aEndTime, aDay, this, aMuseum);
  }

  public boolean addWorkday(WorkDay aWorkday)
  {
    boolean wasAdded = false;
    if (workdays.contains(aWorkday)) { return false; }
    MyMuseum existingMyMuseum = aWorkday.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);
    if (isNewMyMuseum)
    {
      aWorkday.setMyMuseum(this);
    }
    else
    {
      workdays.add(aWorkday);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWorkday(WorkDay aWorkday)
  {
    boolean wasRemoved = false;
    //Unable to remove aWorkday, as it must always have a myMuseum
    if (!this.equals(aWorkday.getMyMuseum()))
    {
      workdays.remove(aWorkday);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addWorkdayAt(WorkDay aWorkday, int index)
  {
    boolean wasAdded = false;
    if(addWorkday(aWorkday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkdays()) { index = numberOfWorkdays() - 1; }
      workdays.remove(aWorkday);
      workdays.add(index, aWorkday);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveWorkdayAt(WorkDay aWorkday, int index)
  {
    boolean wasAdded = false;
    if(workdays.contains(aWorkday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfWorkdays()) { index = numberOfWorkdays() - 1; }
      workdays.remove(aWorkday);
      workdays.add(index, aWorkday);
      wasAdded = true;
    }
    else
    {
      wasAdded = addWorkdayAt(aWorkday, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDisplayRooms()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public DisplayRoom addDisplayRoom(String aName, int aRoomCapacity)
  {
    return new DisplayRoom(aName, aRoomCapacity, this);
  }

  public boolean addDisplayRoom(DisplayRoom aDisplayRoom)
  {
    boolean wasAdded = false;
    if (displayRooms.contains(aDisplayRoom)) { return false; }
    MyMuseum existingMyMuseum = aDisplayRoom.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);
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
    if (!this.equals(aDisplayRoom.getMyMuseum()))
    {
      displayRooms.remove(aDisplayRoom);
      wasRemoved = true;
    }
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Employee addEmployee(String aEmail, String aPassword, double aHourlyWage, double aOverTimeHourlyWage)
  {
    return new Employee(aEmail, aPassword, aHourlyWage, aOverTimeHourlyWage, this);
  }

  public boolean addEmployee(Employee aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    MyMuseum existingMyMuseum = aEmployee.getMyMuseum();
    boolean isNewMyMuseum = existingMyMuseum != null && !this.equals(existingMyMuseum);
    if (isNewMyMuseum)
    {
      aEmployee.setMyMuseum(this);
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
    //Unable to remove aEmployee, as it must always have a myMuseum
    if (!this.equals(aEmployee.getMyMuseum()))
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
  public static int minimumNumberOfOpeningHours()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public WorkDay addOpeningHour(String aStartTime, String aEndTime, Date aDay, MyMuseum aMyMuseum)
  {
    return new WorkDay(aStartTime, aEndTime, aDay, aMyMuseum, this);
  }

  public boolean addOpeningHour(WorkDay aOpeningHour)
  {
    boolean wasAdded = false;
    if (openingHours.contains(aOpeningHour)) { return false; }
    MyMuseum existingMuseum = aOpeningHour.getMuseum();
    boolean isNewMuseum = existingMuseum != null && !this.equals(existingMuseum);
    if (isNewMuseum)
    {
      aOpeningHour.setMuseum(this);
    }
    else
    {
      openingHours.add(aOpeningHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOpeningHour(WorkDay aOpeningHour)
  {
    boolean wasRemoved = false;
    //Unable to remove aOpeningHour, as it must always have a museum
    if (!this.equals(aOpeningHour.getMuseum()))
    {
      openingHours.remove(aOpeningHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOpeningHourAt(WorkDay aOpeningHour, int index)
  {
    boolean wasAdded = false;
    if(addOpeningHour(aOpeningHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOpeningHours()) { index = numberOfOpeningHours() - 1; }
      openingHours.remove(aOpeningHour);
      openingHours.add(index, aOpeningHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOpeningHourAt(WorkDay aOpeningHour, int index)
  {
    boolean wasAdded = false;
    if(openingHours.contains(aOpeningHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOpeningHours()) { index = numberOfOpeningHours() - 1; }
      openingHours.remove(aOpeningHour);
      openingHours.add(index, aOpeningHour);
      wasAdded = true;
    }
    else
    {
      wasAdded = addOpeningHourAt(aOpeningHour, index);
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
    while (artifacts.size() > 0)
    {
      Artifact aArtifact = artifacts.get(artifacts.size() - 1);
      aArtifact.delete();
      artifacts.remove(aArtifact);
    }

    while (loans.size() > 0)
    {
      Loan aLoan = loans.get(loans.size() - 1);
      aLoan.delete();
      loans.remove(aLoan);
    }

    while (museumPasses.size() > 0)
    {
      MuseumPass aMuseumPass = museumPasses.get(museumPasses.size() - 1);
      aMuseumPass.delete();
      museumPasses.remove(aMuseumPass);
    }

    while (visitors.size() > 0)
    {
      Visitor aVisitor = visitors.get(visitors.size() - 1);
      aVisitor.delete();
      visitors.remove(aVisitor);
    }

    while (workdays.size() > 0)
    {
      WorkDay aWorkday = workdays.get(workdays.size() - 1);
      aWorkday.delete();
      workdays.remove(aWorkday);
    }

    StorageRoom existingStorageRoom = storageRoom;
    storageRoom = null;
    if (existingStorageRoom != null)
    {
      existingStorageRoom.delete();
    }
    while (displayRooms.size() > 0)
    {
      DisplayRoom aDisplayRoom = displayRooms.get(displayRooms.size() - 1);
      aDisplayRoom.delete();
      displayRooms.remove(aDisplayRoom);
    }

    while (employees.size() > 0)
    {
      Employee aEmployee = employees.get(employees.size() - 1);
      aEmployee.delete();
      employees.remove(aEmployee);
    }

    while (openingHours.size() > 0)
    {
      WorkDay aOpeningHour = openingHours.get(openingHours.size() - 1);
      aOpeningHour.delete();
      openingHours.remove(aOpeningHour);
    }

  }


  public String toString()
  {
    return super.toString() + "["+
            "pricePerPass" + ":" + getPricePerPass()+ "," +
            "address" + ":" + getAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "president = "+(getPresident()!=null?Integer.toHexString(System.identityHashCode(getPresident())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "storageRoom = "+(getStorageRoom()!=null?Integer.toHexString(System.identityHashCode(getStorageRoom())):"null");
  }
  public void setMuseumId(Long museumId) {
    this.museumId = museumId;
  }

  public Long getMuseumId() {
    return museumId;
  }
}



