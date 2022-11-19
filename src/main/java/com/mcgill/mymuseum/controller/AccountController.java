package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.model.Account;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody AccountDTO account){
        AccountService.AccountType accountType;
        try {
            accountType = AccountService.AccountType.valueOf(account.accountType);
        } catch(IllegalArgumentException err){
            System.out.println("Catching");
            return new ResponseEntity<>("Invalid Data",HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
        Long out = accountService.createAccount(account.email, account.getPassword(), accountType);
        return new ResponseEntity<>(out,HttpStatus.OK);
        } catch (Error err) {
            return new ResponseEntity<>(err.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody AccountDTO account){
        try {
            Long out = accountService.loginAccount(account.email, account.getPassword());
            return new ResponseEntity<>(out,HttpStatus.OK);
        } catch (Error err) {
            return new ResponseEntity<>(err.getMessage() ,HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/remove/{rid}/{id}")
    public ResponseEntity removeUser(@PathVariable(name="id") Long id, @PathVariable(name = "rid") Long requesterId){
        if (accountService.authenticate(requesterId,id, AccountService.Action.INFO)) {
            try {
                boolean out = accountService.removeAccount(requesterId);
                return new ResponseEntity<>(out, HttpStatus.OK);
            } catch (Error err) {
                return new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/info/{rid}/{id}")
    public ResponseEntity getAccount(@PathVariable(name="id") Long id, @PathVariable(name = "rid") Long requesterId){
        if (accountService.authenticate(requesterId,id, AccountService.Action.INFO)){
            Account account = accountService.findAccountByID(id);
            AccountDTO dto = new AccountDTO();
            dto.email = account.getEmail();
            dto.accountType = account instanceof President ? "PRESIDENT" : account instanceof  Employee ? "EMPLOYEE" : account instanceof Visitor ? "VISITOR" : null;
            if (dto.accountType=="EMPLOYEE"){
                dto.hourlyWage = ((Employee) account).getHourlyWage();
                dto.overTimeHourlyWage = ((Employee) account).getOverTimeHourlyWage();
            }
            return new ResponseEntity<>(dto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}
