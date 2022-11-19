package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.*;
import com.mcgill.mymuseum.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    WorkDayRepository workDayRepository;

    @AfterEach
    public void clearDatabase(){
        accountRepository.deleteAll();
    }

    @Test
    public void testRegisterEmployee(){
        String email = "test@email.com";
        String password = "password";
        Long id = employeeService.registerEmployee(email, password);

        Account account = accountService.findAccountByID(id);
        System.out.println(account.toString());

        assertEquals(accountService.findAccountByID(id).getEmail(), email);
        assertEquals(accountService.findAccountByID(id).getPassword(), password);
    }

    @Test
    public void testRemoveEmployee(){

    }

    @Test
    public void testRetrieveEmployeesalary(){

    }

    @Test
    public void testRetrieveEmployee(){

    }

}
