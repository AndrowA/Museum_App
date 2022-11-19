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

    @Transactional
    public Long registerEmployee(String email, String password){
        return accountService.createAccount(email , password, AccountService.AccountType.EMPLOYEE);
    }

    @Transactional
    public boolean removeEmployee(Long targetId){return accountService.removeAccount(targetId);}

    @Transactional
    public Double setEmployeeSalary(Double hourlyWage, Double overTimeHourlyWage, Long targetId){
        employeeRepository.findById(targetId).get().setHourlyWage(hourlyWage);
        employeeRepository.findById(targetId).get().setOverTimeHourlyWage(overTimeHourlyWage);
        return employeeRepository.findById(targetId).get().getHourlyWage();
    }

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

    // Schedule related methods

    @Transactional
    public WorkDay saveWorkDay(WorkDay workDay)  {
        return workDayRepository.save(workDay);
    }

    @Transactional
    public WorkDay getWorkDayByDate(Date date, Long employeeId)  {
        return workDayRepository.findWorkDayByDayAndEmployeeAccountId(date, employeeId);
    }

    @Transactional
    public List<WorkDay> getSchedule(Long employeeId) {
        return workDayRepository.findWorkDaysByEmployee_AccountId(employeeId);
    }

    @Transactional
    public WorkDay deleteWorkDay(Date date, Long employeeId) {
        return workDayRepository.deleteWorkDayByDayAndEmployeeAccountId(date, employeeId);
    }
}
