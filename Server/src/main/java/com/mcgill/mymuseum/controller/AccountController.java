package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.model.Account;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    VisitorService visitorService;

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
        Long out = accountService.createAccount(account.getEmail(), account.getPassword(), accountType);
        return new ResponseEntity<>(out,HttpStatus.OK);
        } catch (Error err) {
            return new ResponseEntity<>(err.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/setName")
    public ResponseEntity setName(@RequestBody AccountDTO account){
        if (accountService.setAccountName(account.getId(),account.getFirstName(),account.getLastName())){
            return new ResponseEntity<>("Name successfully updated",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Could not set names", HttpStatus.INTERNAL_SERVER_ERROR);
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
            Long out = accountService.loginAccount(account.getEmail(), account.getPassword());
            return new ResponseEntity<>(out,HttpStatus.OK);
        } catch (Error err) {
            return new ResponseEntity<>("User not found" ,HttpStatus.NOT_FOUND);
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
                boolean out = accountService.removeAccount(id);
                return new ResponseEntity<>(out, HttpStatus.OK);
            } catch (Error err) {
                return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>("Invalid Permissions",HttpStatus.FORBIDDEN);
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
            dto.setFirstName(account.getFirstName());
            dto.setLastName(account.getLastName());
            dto.setEmail(account.getEmail());
            dto.setAccountType(account instanceof President ? "PRESIDENT" : account instanceof  Employee ? "EMPLOYEE" : account instanceof Visitor ? "VISITOR" : null);
            if (dto.getAccountType()=="EMPLOYEE"){
                dto.setHourlyWage(((Employee) account).getHourlyWage());
                dto.setOverTimeHourlyWage(((Employee) account).getOverTimeHourlyWage());
            }
            return new ResponseEntity<>(dto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid Permissions",HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Getter method to get all the visitors
     * @param rid of the requester in question
     * @return ResponseEntity of Visitors and HTTP status
     * @throws Exception
     */
    @GetMapping("/getVisitors/{rid}")
    public ResponseEntity getVisitors(@PathVariable long rid) throws Exception {
        System.out.println("got here");
        if (accountService.authenticate(rid, rid, AccountService.Action.INFO)) {

            //get employee by ID
            try {
                List<AccountDTO> visitorsDTO = new ArrayList<AccountDTO>();
                Iterable<Account> loopingList = visitorService.retrieveAllVisitors();
                for (Account visitor : loopingList) {
                    if(accountService.findTargetType(visitor.getAccountId()).equals(AccountService.TargetType.VISITOR)) {
                        String email = visitor.getEmail();
                        String password = visitor.getPassword();
                        String accountType = "VISITOR";
                        AccountDTO visitorDTO = new AccountDTO(email,password,accountType);
                        visitorDTO.setId(visitor.getAccountId());
                        visitorsDTO.add(visitorDTO);
                    }
                }
                return new ResponseEntity<>(visitorsDTO, HttpStatus.OK);
            } catch(Exception e) {
                return new ResponseEntity<>("No visitors found", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Invalid Permissions",HttpStatus.FORBIDDEN);
        }
    }

}
