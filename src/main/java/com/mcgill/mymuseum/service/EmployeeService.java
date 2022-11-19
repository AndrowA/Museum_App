package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.Account;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

}
