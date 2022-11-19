package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.dto.EmployeeDTO;
import com.mcgill.mymuseum.model.Account;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    AccountService accountService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity registerEmployeeSalary(@RequestBody EmployeeDTO employee){
        try {
            Long out = accountService.createAccount(employee.email, employee.getPassword(), AccountService.AccountType.EMPLOYEE);
            return new ResponseEntity<>(out,HttpStatus.OK);
        } catch (Error err) {
            return new ResponseEntity<>(err.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/remove/{rid}/{id}")
    public ResponseEntity removeEmployee(@PathVariable(name="id") Long id, @PathVariable(name = "rid") Long requesterId){
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

    @PostMapping("/salary/{rid}/{id}")
    public ResponseEntity setEmployeeSalary(@PathVariable(name="id") Long id, @PathVariable(name = "rid") Long requesterId, Double hourlyWage, Double overTimeHourlyWage) {
        if (accountService.authenticate(requesterId,id, AccountService.Action.MODIFY)) {
            try {
                boolean out = employeeService.setEmployeeSalary(hourlyWage, overTimeHourlyWage, id, requesterId);
                return new ResponseEntity<>(out, HttpStatus.OK);
            } catch (Error err) {
                return new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_FOUND);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/info/{rid}/{id}")
    public ResponseEntity getEmployee(@PathVariable(name="id") Long id, @PathVariable(name = "rid") Long requesterId) {
        if (accountService.authenticate(requesterId,id, AccountService.Action.INFO)){
            Employee employee = employeeService.retrieveEmployee(id,requesterId);
            EmployeeDTO dto = new EmployeeDTO();
            dto.email = employee.getEmail();
            dto.hourlyWage = employee.getHourlyWage();
            dto.overTimeHourlyWage = employee.getOverTimeHourlyWage();
            return new ResponseEntity<>(dto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
