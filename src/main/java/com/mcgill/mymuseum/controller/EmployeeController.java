package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.dto.EmployeeDTO;
import com.mcgill.mymuseum.model.Account;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.EmployeeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.dto.LoanDTO;
import com.mcgill.mymuseum.dto.WorkDayDTO;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.model.WorkDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    AccountService accountService;

    @Autowired

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
        if (accountService.authenticate(requesterId, id, AccountService.Action.INFO)) {
            try {
                boolean out = employeeService.removeEmployee(id);
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
                Double out = employeeService.setEmployeeSalary(hourlyWage, overTimeHourlyWage, id);
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
            Employee employee = employeeService.retrieveEmployee(id);
            EmployeeDTO dto = new EmployeeDTO();
            dto.email = employee.getEmail();
            dto.hourlyWage = employee.getHourlyWage();
            dto.overTimeHourlyWage = employee.getOverTimeHourlyWage();
            return new ResponseEntity<>(dto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    EmployeeService employeeService;
    @GetMapping("/{employeeId}/schedule")
    public ResponseEntity getSchedule(@PathVariable long employeeId) throws Exception {
        //get employee by ID
        Employee employee = employeeService.getEmploye(employeeId);
        List<WorkDayDTO> scheduleDTO = new ArrayList<WorkDayDTO>();
        for(WorkDay workDay : employee.getSchedule()) {
            String startTime = workDay.getStartTime();
            String endTime = workDay.getEndTime();
            Date day = workDay.getDay();
            String employeeName = employee.getEmail();
            WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeName, employeeId);
            scheduleDTO.add(workDayDTO);
        }
        return new ResponseEntity<>(scheduleDTO, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/{employeeId}/schedule/addWorkDay")
    public ResponseEntity addWorkDay(@PathVariable long employeeId, @RequestBody String body) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            WorkDay newWorkDay = mapper.readValue(body, WorkDay.class);
            Employee employee = employeeService.getEmploye(employeeId);
            employee.addSchedule(newWorkDay);
            WorkDay savedWorkday = employeeService.saveWorkDay(newWorkDay);

            String startTime = savedWorkday.getStartTime();
            String endTime = savedWorkday.getEndTime();
            Date day = savedWorkday.getDay();
            String employeeName = employee.getEmail();
            WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeName, employeeId);

            return new ResponseEntity<>(workDayDTO, HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(method = RequestMethod.POST, path = "/{employeeId}/schedule/ModifyWorkDay")
    public ResponseEntity ModifyWorkDay(@PathVariable long employeeId, @RequestBody String body) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            WorkDay newWorkDay = mapper.readValue(body, WorkDay.class);
            Date date =  newWorkDay.getDay();
            WorkDay oldWorkDay  = employeeService.getWorkDayByDate(date, employeeId);
            newWorkDay.setId(oldWorkDay.getId());

            WorkDay savedWorkday = employeeService.saveWorkDay(newWorkDay);

            String startTime = savedWorkday.getStartTime();
            String endTime = savedWorkday.getEndTime();
            Date day = savedWorkday.getDay();
            String employeeName = employee.getEmail();
            WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeName, employeeId);

            return new ResponseEntity<>(workDayDTO, HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(method = RequestMethod.POST, path = "/{employeeId}/schedule/DeleteWorkDay")
    public ResponseEntity ModifyWorkDay(@PathVariable long employeeId, @RequestBody String body) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try {
            WorkDay deletingWorkDay = mapper.readValue(body, WorkDay.class);
            Date date =  deletingWorkDay.getDay();

            employeeService.deleteWorkDay(date, employeeId);

            WorkDay savedWorkday = employeeService.saveWorkDay(newWorkDay);


            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


