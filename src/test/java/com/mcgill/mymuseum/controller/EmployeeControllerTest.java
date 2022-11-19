package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.dto.EmployeeDTO;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.President;
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

        ResponseEntity<Long> out1 =  employeeController.registerEmployee(employeeDTO);

        ResponseEntity<Boolean> out2 = employeeController.removeEmployee(out1.getBody(),out1.getBody());

        assertEquals(out2.getStatusCode(),HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetEmployeeValidReq(){
        String email = "test@example.com";
        String password = "password";
        String type = "VISITOR";
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
        String type = "VISITOR";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType(type);

        ResponseEntity<Long> out1 = accountController.registerUser(accountDTO);
        // simlulate user getting their own account
        ResponseEntity<AccountDTO> out2  = accountController.getAccount(out1.getBody(),Long.valueOf(0));

        assertEquals(out2.getStatusCode(),HttpStatus.FORBIDDEN);
    }

}
