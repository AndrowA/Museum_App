package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.dto.EmployeeDTO;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.EmployeeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.dto.WorkDayDTO;
import com.mcgill.mymuseum.model.WorkDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

    @Autowired
    AccountService accountService;


    @Autowired
    EmployeeService employeeService;

    /**
     * Post method for Registering an employee
     * @param employee
     * @return ResponseEntity of DTO and HTTP status
     */
    @PostMapping("/register")
    public ResponseEntity registerEmployee(@RequestBody AccountDTO employee) {
        if (employee.getAccountType().equals("EMPLOYEE")){
            try {
                Long out = accountService.createAccount(employee.email, employee.getPassword(), AccountService.AccountType.EMPLOYEE);
                return new ResponseEntity<>(out, HttpStatus.OK);
            } catch (Error err) {
                return new ResponseEntity<>(err.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<>("Invalid Data",HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Post method for removing an employee
     * @param id of the employee to remove
     * @param requesterId of the person trying to remove the employee
     * @return ResponseEntity of boolean and HTTP status
     */
    @PostMapping("/remove/{rid}/{id}")
    public ResponseEntity removeEmployee(@PathVariable(name="id") Long id, @PathVariable(name = "rid") Long requesterId){
        if (accountService.authenticate(requesterId, id, AccountService.Action.REMOVE)) {
            try {
                Boolean out = employeeService.removeEmployee(id);
                return new ResponseEntity<>(out, HttpStatus.OK);
            } catch (Error err) {
                return new ResponseEntity<>(err.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Post method for Setting the salary of an employee
     * @param id of the employee to remove
     * @param requesterId of the person trying to remove the employee
     * @param hourlyWage of the employee
     * @param overTimeHourlyWage of the employee
     * @return ResponseEntity of the hourlyWage of the employee and HTTP status
     */
    @PostMapping("/salary/{rid}/{id}")
    public ResponseEntity setEmployeeSalary(@PathVariable(name = "id") Long id, @PathVariable(name = "rid") Long requesterId, Double hourlyWage, Double overTimeHourlyWage) {
        if (accountService.authenticate(requesterId, id, AccountService.Action.REMOVE)) {
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

    /**
     * Getter method to get an employee
     * @param id of the employee in question
     * @param requesterId of the person requesting the employee info
     * @return ResponseEntity of Employee and HTTP status
     */
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

    /**
     * Getter method to get all the employees
     * @param rid of the requester in question
     * @return ResponseEntity of Employees and HTTP status
     * @throws Exception
     */
    @GetMapping("/getEmployees/{rid}")
    public ResponseEntity getEmployees(@PathVariable long rid) throws Exception {
        if (accountService.authenticate(rid, rid, AccountService.Action.INFO)) {
            //get employee by ID
            try {
                List<AccountDTO> employeesDTO = new ArrayList<AccountDTO>();
                Iterable<Employee> loopingList = employeeService.retrieveAllEmployees();
                for (Employee employee : loopingList) {
                    Long id = employee.getAccountId();
                    String email = employee.getEmail();
                    String password = employee.getPassword();
                    String accountType = "EMPLOYEE";
                    AccountDTO employeeDTO = new AccountDTO(email,password,accountType);
                    employeeDTO.setHourlyWage(25.0);
                    employeeDTO.setOverTimeHourlyWage(50.0);
                    employeeDTO.setId(employee.getAccountId());
                    employeesDTO.add(employeeDTO);
                }
                return new ResponseEntity<>(employeesDTO, HttpStatus.OK);
            } catch(Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Getter method to get the schedule of an employee
     * @param employeeId of the employee in question
     * @param requesterId of the person requesting the employee schedule
     * @return ResponseEntity of Schedule and HTTP status
     * @throws Exception
     */
    @GetMapping("/schedule/getSchedule/{rid}/{employeeId}")
    public ResponseEntity getSchedule(@PathVariable long employeeId, @PathVariable(name = "rid") Long requesterId) throws Exception {
        if (accountService.authenticate(requesterId, employeeId, AccountService.Action.INFO)) {
            //get employee by ID
            try {
                Employee employee = employeeService.retrieveEmployee(employeeId);
                List<WorkDayDTO> scheduleDTO = new ArrayList<WorkDayDTO>();
                List<WorkDay> loopingList = employeeService.getSchedule(employeeId);
                System.out.println(loopingList.get(0));
                for (WorkDay workDay : loopingList) {
                    String startTime = workDay.getStartTime();
                    System.out.println(startTime);
                    String endTime = workDay.getEndTime();
                    Date day = workDay.getDay();
                    String employeeName = employee.getEmail();
                    WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeName, employeeId);
                    scheduleDTO.add(workDayDTO);
                }
                System.out.println(scheduleDTO);
                System.out.println(scheduleDTO.get(0).getStartTime());
                return new ResponseEntity<>(scheduleDTO, HttpStatus.OK);
            } catch(Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Getter method to get an employee's workday by date
     * @param date of a workday
     * @param employeeId of the employee in question
     * @param requesterId of the person requesting the employee schedule
     * @return ResponseEntity of Workday and HTTP status
     * @throws Exception
     */
    @GetMapping("/schedule/getWorkDayByDateAndId/{rid}/{employeeId}")
    public ResponseEntity getWorkDayByDate(@RequestBody String date, @PathVariable long employeeId, @PathVariable(name = "rid") Long requesterId) throws Exception {
        if (accountService.authenticate(requesterId, employeeId, AccountService.Action.INFO)) {
            //get employee by ID
            Employee employee = employeeService.retrieveEmployee(employeeId);
            try {
                WorkDay workDay = employeeService.getWorkDayByDate(Date.valueOf(date), employeeId);
                System.out.println("this is the work day:" + workDay);
                String startTime = workDay.getStartTime();
                String endTime = workDay.getEndTime();
                Date day = workDay.getDay();
                String employeeName = employee.getEmail();
                WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeName, employeeId);
                return new ResponseEntity<>(workDayDTO, HttpStatus.OK);
            } catch(Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Add an employee's workday
     * @param employeeId of the employee in question
     * @param requesterId of the person adding the employee's workday
     * @param body of the post request
     * @return ResponseEntity of Workday and HTTP status
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/schedule/{rid}/{employeeId}/addWorkDay")
    public ResponseEntity addWorkDay(@PathVariable long employeeId, @PathVariable(name = "rid") Long requesterId, @RequestBody String body) throws Exception {
        if (accountService.authenticate(requesterId, employeeId, AccountService.Action.ASSIGN)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                WorkDay newWorkDay = mapper.readValue(body, WorkDay.class);
                Employee employee = employeeService.retrieveEmployee(employeeId);
                employee.addSchedule(newWorkDay);
                WorkDay savedWorkday = employeeService.saveWorkDay(newWorkDay);

                String startTime = savedWorkday.getStartTime();
                String endTime = savedWorkday.getEndTime();
                Date day = savedWorkday.getDay();
                String employeeName = employee.getEmail();
                WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeName, employeeId);

                return new ResponseEntity<>(workDayDTO, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Modify an employee's workday
     * @param employeeId of the employee in question
     * @param requesterId of the person modifying the employee's workday
     * @param body of the post request
     * @return ResponseEntity of Workday and HTTP status
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/schedule/{rid}/{employeeId}/ModifyWorkDay")
    public ResponseEntity modifyWorkDay(@PathVariable long employeeId, @PathVariable(name = "rid") Long requesterId, @RequestBody String body) throws Exception {
        if (accountService.authenticate(requesterId, employeeId, AccountService.Action.MODIFY)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                WorkDay newWorkDay = mapper.readValue(body, WorkDay.class);
                Date date = newWorkDay.getDay();
                WorkDay oldWorkDay = employeeService.getWorkDayByDate(date, employeeId);
                newWorkDay.setId(oldWorkDay.getId());
                WorkDay savedWorkday = employeeService.saveWorkDay(newWorkDay);

                String startTime = savedWorkday.getStartTime();
                String endTime = savedWorkday.getEndTime();
                Date day = savedWorkday.getDay();
                Employee employee = employeeService.retrieveEmployee(employeeId);
                String employeeName = employee.getEmail();
                WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeName, employeeId);

                return new ResponseEntity<>(workDayDTO, HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * Delete an employee's workday
     * @param employeeId of the employee in question
     * @param requesterId of the person deleting the employee's workday
     * @param body of the post request
     * @return ResponseEntity of HTTP status
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, path = "/schedule/{rid}/{employeeId}/DeleteWorkDay")
    public ResponseEntity deleteWorkDay(@RequestBody String body, @PathVariable long employeeId, @PathVariable(name = "rid") Long requesterId) throws Exception {
        if (accountService.authenticate(requesterId, employeeId, AccountService.Action.MODIFY)) {
            System.out.println(body);
            Employee employee = employeeService.retrieveEmployee(employeeId);
            ObjectMapper mapper = new ObjectMapper();
            try {
                WorkDay newWorkDay = mapper.readValue(body, WorkDay.class);
                Date date = newWorkDay.getDay();
                Integer workDay = employeeService.deleteWorkDay(date, employeeId);
                System.out.println(workDay);
                if(workDay != null) {
                    System.out.println("less go");
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    System.out.println("option 2");
                    return new ResponseEntity<>("not found 1",HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("option 3");
                return new ResponseEntity<>("not found 1",HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
