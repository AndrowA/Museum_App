package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.model.Employee;
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
    public void testRegisterUserValidReq(){
        // account DTO
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType(type);

       ResponseEntity<Long> out =  accountController.registerUser(accountDTO);

       assertTrue(out.getStatusCode().equals(HttpStatus.OK));
       assertEquals(email, accountService.findAccountByID(out.getBody()).getEmail());
       assertEquals(password, accountService.findAccountByID(out.getBody()).getPassword());

    }

}
