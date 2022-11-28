package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.*;
import com.mcgill.mymuseum.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Androw Abd El Malak
 * @author Daniel Makhlin
 */

@Service
public class EmployeeService {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WorkDayRepository workDayRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    /**
     * Service to register an employee
     * @param email of employee
     * @param password of password
     * @return accountId
     */
    @Transactional
    public Long registerEmployee(String email, String password){
        return accountService.createAccount(email , password, AccountService.AccountType.EMPLOYEE);
    }

    /**
     * Service to remove an employee
     * @param targetId of employee
     * @return boolean
     */
    @Transactional
    public boolean removeEmployee(Long targetId){return accountService.removeAccount(targetId);}

    /**
     * Service to set the employee's salary
     * @param hourlyWage of employee
     * @param overTimeHourlyWage of employee
     * @param targetId of employee
     * @return hourlyWage of employee
     */

    @Transactional
    public Double setEmployeeSalary(Double hourlyWage, Double overTimeHourlyWage, Long targetId){
        employeeRepository.findById(targetId).get().setHourlyWage(hourlyWage);
        employeeRepository.findById(targetId).get().setOverTimeHourlyWage(overTimeHourlyWage);
        return employeeRepository.findById(targetId).get().getHourlyWage();
    }

    /**
     * Service to retreive employee
     * @param targetId of employee
     * @return Employee
     */

    @Transactional
    public Employee retrieveEmployee(Long targetId) {
        try {
            Optional<Account> optionalEmployee = accountRepository.findById(targetId);
            Account a = optionalEmployee.get();
            if (a instanceof Employee) {
                return (Employee) a;
            } else {
                return null;
            }
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    /**
     * Service to retreive all employees
     * @return a list of Employees
     */

    @Transactional
    public Iterable<Employee> retrieveAllEmployees() {return employeeRepository.findAll();}

    // Schedule related methods

    /**
     * Service to save the workday
     * @param workDay of the employee
     * @return WorkDay
     */
    @Transactional
    public WorkDay saveWorkDay(WorkDay workDay)  {
        return workDayRepository.save(workDay);
    }

    /**
     * Service to get the workday by date
     * @param date of the workday
     * @param employeeId of the employee
     * @return WorkDay
     */
    @Transactional
    public WorkDay getWorkDayByDate(Date date, Long employeeId)  {
        return workDayRepository.findWorkDayByDayAndEmployeeAccountId(date, employeeId);
    }

    /**
     * Service to get the schedule of an employee
     * @param employeeId of the employee
     * @return List of WorkDays
     */
    @Transactional
    public List<WorkDay> getSchedule(Long employeeId) {
        return workDayRepository.findWorkDaysByEmployee_AccountId(employeeId);
    }

    /**
     * Service to delete workday
     * @param date of the workday
     * @param employeeId of the employee
     * @return WorkDay
     */
    @Transactional
    public Integer deleteWorkDay(Date date, Long employeeId) {
        return workDayRepository.deleteWorkDayByDayAndEmployeeAccountId(date, employeeId);
    }
}
