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

    /**
     * Controller method to register an account
     * @param account body of an account DTO as a post request
     * @return ResponseEntity of account if successful or HTTP status
     */
    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody AccountDTO account){
        AccountService.AccountType accountType;
        try {
            accountType = AccountService.AccountType.valueOf(account.getAccountType());
        } catch(IllegalArgumentException err){
            return new ResponseEntity<>("Invalid Data",HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
        Long out = accountService.createAccount(account.email, account.getPassword(), accountType);
        return new ResponseEntity<>(out,HttpStatus.OK);
        } catch (Error err) {
            return new ResponseEntity<>(err.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Controller method to login into system
     * @param account body of an account DTO as a post request
     * @return ResponseEntity of account if successful or HTTP status
     */
    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody AccountDTO account){
        try {
            Long out = accountService.loginAccount(account.email, account.getPassword());
            return new ResponseEntity<>(out,HttpStatus.OK);
        } catch (Error err) {
            return new ResponseEntity<>(err.getMessage() ,HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Controller method to remove an account
     * @param id of account to be removed
     * @param requesterId
     * @return ResponseEntity of account if successful or HTTP status
     */
    @PostMapping("/remove/{rid}/{id}")
    public ResponseEntity removeUser(@PathVariable(name="id") Long id, @PathVariable(name = "rid") Long requesterId){
        if (accountService.authenticate(requesterId,id, AccountService.Action.REMOVE)) {
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

    /**
     *
     * @param id
     * @param requesterId
     * @return
     */
    @GetMapping("/info/{rid}/{id}")
    public ResponseEntity getAccount(@PathVariable(name="id") Long id, @PathVariable(name = "rid") Long requesterId){
        if (accountService.authenticate(requesterId,id, AccountService.Action.INFO)){
            Account account = accountService.findAccountByID(id);
            AccountDTO dto = new AccountDTO();
            dto.email = account.getEmail();
            dto.setAccountType(account instanceof President ? "PRESIDENT" : account instanceof  Employee ? "EMPLOYEE" : account instanceof Visitor ? "VISITOR" : null);
            if (dto.getAccountType()=="EMPLOYEE"){
                dto.setHourlyWage(((Employee) account).getHourlyWage());
                dto.setOverTimeHourlyWage(((Employee) account).getOverTimeHourlyWage());
            }
            return new ResponseEntity<>(dto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

}
