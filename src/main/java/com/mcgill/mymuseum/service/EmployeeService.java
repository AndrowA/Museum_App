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
    public boolean removeEmployee(Long id){
        return accountService.removeAccount(id);
    }

    @Transactional
    public boolean setEmployeeSalary(Double hourlyWage, Double overTimeHourlyWage,long accountId, long targetId){
        if (accountService.authenticate(accountId, targetId, AccountService.Action.INFO)) {
            employeeRepository.findById(targetId).get().setHourlyWage(hourlyWage);
            employeeRepository.findById(targetId).get().setOverTimeHourlyWage(overTimeHourlyWage);
            return true;
        }

        return false;

    }

    @Transactional
    public Employee retrieveEmployee(long accountId, long targetId) {
        if (accountService.authenticate(accountId, targetId, AccountService.Action.INFO)) {
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
        }else{
            return null;
        }
    }

}
