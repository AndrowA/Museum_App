package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.service.AccountService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountControllerTest {

    @Autowired
    AccountController accountController;

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

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
        assertEquals(out2.getBody(), "Incorrect Email or Password");
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

}
