package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.repository.LoanRepository;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import com.mcgill.mymuseum.repository.WorkDayRepository;
import com.mcgill.mymuseum.service.AccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountControllerTest {

    @Autowired
    AccountController accountController;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    WorkDayRepository workDayRepository;

    @Autowired
    MuseumPassRepository museumPassRepository;

    @Autowired
    LoanRepository loanRepository;

    @AfterEach
    public void clearDatabase(){
        workDayRepository.deleteAll();
        museumPassRepository.deleteAll();
        loanRepository.deleteAll();
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

    @Test
    public void testRegisterUserInvalidReq(){
        String email = "test@example.com";
        String password = "password";
        String type = "WRONGTYPE";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType(type);

        ResponseEntity out =  accountController.registerUser(accountDTO);

        assertTrue(out.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
        assertEquals("Invalid Data", out.getBody());
    }

    @Test
    public void testLoginUserValidReq(){

        String email = "test@example.com";
        String password = "password";
        String type = "VISITOR";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType(type);

        ResponseEntity<Long> out1 = accountController.registerUser(accountDTO);
        ResponseEntity<Long> out2  = accountController.loginUser(accountDTO);

        assertEquals(out1, out2);
    }

    @Test
    public void testSetNameValidReq(){
        String email = "test@example.com";
        String password = "password";
        String firstName = "First";
        String lastName = "Last";
        String type = "EMPLOYEE";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setFirstName(firstName);
        accountDTO.setLastName(lastName);
        accountDTO.setAccountType(type);
        Long userId = accountService.createAccount(email,password, AccountService.AccountType.EMPLOYEE);
        accountDTO.setId(userId);
        accountController.setName(accountDTO);
        assertEquals(firstName, accountRepository.findById(userId).get().getFirstName());
        assertEquals(lastName, accountRepository.findById(userId).get().getLastName());
    }

    @Test
    public void testSetNameInvalidReq(){
        String firstName = "First";
        String lastName = "Last";
        String type = "EMPLOYEE";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(0l);
        accountDTO.setFirstName(firstName);
        accountDTO.setLastName(lastName);
        accountDTO.setAccountType(type);
        ResponseEntity response = accountController.setName(accountDTO);
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Test
    public void testLoginUserInvalidReq(){

        String email = "test@example.com";
        String password = "password";
        String type = "VISITOR";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType(type);

        ResponseEntity<Long> out1 = accountController.registerUser(accountDTO);

        String wrongPassword = "wrong_password";

        accountDTO.setPassword(wrongPassword);

        ResponseEntity out2  = accountController.loginUser(accountDTO);

        assertNotEquals(out1, out2);
        assertEquals(out2.getBody(), "Invalid Email or Password");
    }

    @Test
    public void testGetAccountValidReq(){
        String email = "test@example.com";
        String password = "password";
        String type = "VISITOR";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType(type);

        ResponseEntity<Long> out1 = accountController.registerUser(accountDTO);
        // simlulate user getting their own account
        ResponseEntity<AccountDTO> out2  = accountController.getAccount(out1.getBody(),out1.getBody());

        assertEquals(out2.getStatusCode(),HttpStatus.OK);
        assertEquals(accountDTO.getEmail(),out2.getBody().getEmail());
        assertEquals(accountDTO.getAccountType(),out2.getBody().getAccountType());

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

    @Test
    public void testRemoveEmployeeValidReq(){

        // Employee DTO
        String email = "test@example.com";
        String password = "password";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType("EMPLOYEE");

        //President DTO
        President president = new President();
        president = accountRepository.save(president);

        ResponseEntity<Long> out1 =  accountController.registerUser(accountDTO);
        ResponseEntity<Boolean> out2 = accountController.removeUser(out1.getBody(), president.getAccountId());

        assertEquals(out2.getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void testRemoveEmployeeInvalidReq(){
        // Employee DTO
        String email = "test@example.com";
        String password = "password";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType("EMPLOYEE");

        ResponseEntity<Long> out1 =  accountController.registerUser(accountDTO);
        ResponseEntity<Boolean> out2 = accountController.removeUser(out1.getBody(),out1.getBody());

        assertEquals(out2.getStatusCode(),HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetVisitorsValidReq() throws Exception {
        String email = "test@example.com";
        String password = "password";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType("PRESIDENT");
        ResponseEntity<Long> presidentResponse =  accountController.registerUser(accountDTO);
        String email1 = "test1@example.com";
        accountDTO.setEmail(email1);
        accountDTO.setAccountType("VISITOR");
        accountController.registerUser(accountDTO);
        String email2 = "test2@example.com";
        accountDTO.setEmail(email2);
        accountController.registerUser(accountDTO);
        String email3 = "test3@example.com";
        accountDTO.setEmail(email3);
        accountController.registerUser(accountDTO);
        String email4 = "test4@example.com";
        accountDTO.setEmail(email4);
        accountController.registerUser(accountDTO);
        String email5 = "test5@example.com";
        accountDTO.setEmail(email5);
        accountController.registerUser(accountDTO);
        String email6 = "test6@example.com";
        accountDTO.setEmail(email6);
        accountController.registerUser(accountDTO);

        ResponseEntity response = accountController.getVisitors(presidentResponse.getBody());
        List<AccountDTO> accountDTOList  = (List<AccountDTO>) response.getBody();
        assertEquals(accountDTOList.get(0).getEmail(), email1);
        assertEquals(accountDTOList.get(1).getEmail(), email2);
        assertEquals(accountDTOList.get(2).getEmail(), email3);
        assertEquals(accountDTOList.get(3).getEmail(), email4);
        assertEquals(accountDTOList.get(4).getEmail(), email5);
        assertEquals(accountDTOList.get(5).getEmail(), email6);

    }

    @Test
    public  void testGetVisitorsInvalidReq() throws Exception {
        String email = "test@example.com";
        String password = "password";
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(email);
        accountDTO.setPassword(password);
        accountDTO.setAccountType("PRESIDENT");
        ResponseEntity response = accountController.getVisitors(0l);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }


}
