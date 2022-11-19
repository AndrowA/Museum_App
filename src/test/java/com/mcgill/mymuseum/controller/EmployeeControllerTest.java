package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.dto.EmployeeDTO;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.repository.EmployeeRepository;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmployeeControllerTest {

    @Autowired
    AccountController accountController;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EmployeeController employeeController;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void clearDatabase(){
        accountRepository.deleteAll();
    }

    @Test
    public void testRegisterEmployeeValidReq(){
        // user DTO
        String email = "test@example.com";
        String password = "password";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType("EMPLOYEE");

       ResponseEntity<Long> out =  employeeController.registerEmployee(employeeDTO);

       assertTrue(out.getStatusCode().equals(HttpStatus.OK));
       assertEquals(email, employeeService.retrieveEmployee(out.getBody()).getEmail());
       assertEquals(password, employeeService.retrieveEmployee(out.getBody()).getPassword());
    }

    @Test
    public void testRegisterEmployeeInvalidReq(){
        // user DTO
        String email = "test@example.com@error";
        String password = "password";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType("EMPLOYEE");

        ResponseEntity<Long> out =  employeeController.registerEmployee(employeeDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,out.getStatusCode());
    }

    @Test
    public void testRemoveEmployeeValidReq(){

        // Employee DTO
        String email = "test@example.com";
        String password = "password";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType("EMPLOYEE");

        //President DTO
        President president = new President();
        president = employeeRepository.save(president);

        ResponseEntity<Long> out1 =  employeeController.registerEmployee(employeeDTO);
        ResponseEntity<Boolean> out2 = employeeController.removeEmployee(out1.getBody(), president.getAccountId());

        assertEquals(out2.getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void testRemoveEmployeeInvalidReq(){
        // Employee DTO
        String email = "test@example.com";
        String password = "password";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType("EMPLOYEE");

        ResponseEntity<Long> out1 =  employeeController.registerEmployee(employeeDTO);

        ResponseEntity<Boolean> out2 = employeeController.removeEmployee(out1.getBody(),out1.getBody());

        assertEquals(out2.getStatusCode(),HttpStatus.FORBIDDEN);
    }

    @Test
    public void testSetEmployeeSalaryValidReq(){
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        Double hourlyWage = 20.0;
        Double overTimeHourlyWage = 30.0;

        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType(type);

        //President DTO
        President president = new President();
        president = employeeRepository.save(president);

        ResponseEntity<Long> out1 =  employeeController.registerEmployee(employeeDTO);
        ResponseEntity out2 = employeeController.setEmployeeSalary(out1.getBody(), president.getAccountId(), hourlyWage, overTimeHourlyWage);

        assertTrue(out2.getStatusCode().equals(HttpStatus.OK));
        assertEquals(hourlyWage,employeeService.retrieveEmployee(out1.getBody()).getHourlyWage());
    }

    @Test
    public void testSetEmployeeSalaryInvalidReq(){
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        Double hourlyWage = 20.0;
        Double overTimeHourlyWage = 30.0;

        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType(type);

        ResponseEntity<Long> out1 =  employeeController.registerEmployee(employeeDTO);
        ResponseEntity out2 = employeeController.setEmployeeSalary(out1.getBody(), out1.getBody(), hourlyWage, overTimeHourlyWage);

        assertEquals(out2.getStatusCode(),HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetEmployeeValidReq(){
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType(type);

        ResponseEntity<Long> out1 = employeeController.registerEmployee(employeeDTO);
        ResponseEntity<EmployeeDTO> out2  = employeeController.getEmployee(out1.getBody(),out1.getBody());

        assertEquals(out2.getStatusCode(),HttpStatus.OK);
        assertEquals(employeeDTO.getEmail(),out2.getBody().getEmail());
    }

    @Test
    public void testGetAccountInvalidReq(){
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType(type);

        //President DTO
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);

        ResponseEntity<Long> out1 = employeeController.registerEmployee(employeeDTO);

        ResponseEntity<EmployeeDTO> out3  = employeeController.getEmployee(out1.getBody(),visitor.getAccountId());

        assertEquals(out3.getStatusCode(),HttpStatus.FORBIDDEN);
    }

}
