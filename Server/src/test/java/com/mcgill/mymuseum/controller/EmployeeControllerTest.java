package com.mcgill.mymuseum.controller;
import com.mcgill.mymuseum.dto.AccountDTO;
import com.mcgill.mymuseum.dto.EmployeeDTO;
import com.mcgill.mymuseum.dto.WorkDayDTO;
import com.mcgill.mymuseum.model.*;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.repository.EmployeeRepository;
import com.mcgill.mymuseum.repository.WorkDayRepository;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    WorkDayRepository workDayRepository;

    @AfterEach
    public void clearDatabase() {
        workDayRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testRegisterEmployeeValidReq() {
        // user DTO
        String email = "test@example.com";
        String password = "password";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType("EMPLOYEE");

        ResponseEntity<Long> out = employeeController.registerEmployee(employeeDTO);

        assertTrue(out.getStatusCode().equals(HttpStatus.OK));
        assertEquals(email, employeeService.retrieveEmployee(out.getBody()).getEmail());
        assertEquals(password, employeeService.retrieveEmployee(out.getBody()).getPassword());
    }

    @Test
    public void testRegisterEmployeeInvalidReq() {
        // user DTO
        String email = "test@example.com@error";
        String password = "password";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType("EMPLOYEE");

        ResponseEntity<Long> out = employeeController.registerEmployee(employeeDTO);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, out.getStatusCode());
    }

    @Test
    public void testRemoveEmployeeValidReq() {

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

        ResponseEntity<Long> out1 = employeeController.registerEmployee(employeeDTO);
        ResponseEntity<Boolean> out2 = employeeController.removeEmployee(out1.getBody(), president.getAccountId());

        assertEquals(out2.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testRemoveEmployeeInvalidReq() {
        // Employee DTO
        String email = "test@example.com";
        String password = "password";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType("EMPLOYEE");

        ResponseEntity<Long> out1 = employeeController.registerEmployee(employeeDTO);

        ResponseEntity<Boolean> out2 = employeeController.removeEmployee(out1.getBody(), out1.getBody());

        assertEquals(out2.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testSetEmployeeSalaryValidReq() {
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        Double hourlyWage = 20.0;
        Double overTimeHourlyWage = 30.0;

        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType(type);

        //President DTO
        President president = new President();
        president = employeeRepository.save(president);

        ResponseEntity<Long> out1 = employeeController.registerEmployee(employeeDTO);
        ResponseEntity out2 = employeeController.setEmployeeSalary(out1.getBody(), president.getAccountId(), hourlyWage, overTimeHourlyWage);

        assertTrue(out2.getStatusCode().equals(HttpStatus.OK));
        assertEquals(hourlyWage, employeeService.retrieveEmployee(out1.getBody()).getHourlyWage());
    }

    @Test
    public void testSetEmployeeSalaryInvalidReq() {
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        Double hourlyWage = 20.0;
        Double overTimeHourlyWage = 30.0;

        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType(type);

        ResponseEntity<Long> out1 = employeeController.registerEmployee(employeeDTO);
        ResponseEntity out2 = employeeController.setEmployeeSalary(out1.getBody(), out1.getBody(), hourlyWage, overTimeHourlyWage);

        assertEquals(out2.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetEmployeeValidReq() {
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType(type);

        ResponseEntity<Long> out1 = employeeController.registerEmployee(employeeDTO);
        ResponseEntity<EmployeeDTO> out2 = employeeController.getEmployee(out1.getBody(), out1.getBody());

        assertEquals(out2.getStatusCode(), HttpStatus.OK);
        assertEquals(employeeDTO.getEmail(), out2.getBody().getEmail());
    }

    @Test
    public void testGetAccountInvalidReq() {
        String email = "test@example.com";
        String password = "password";
        String type = "EMPLOYEE";
        AccountDTO employeeDTO = new AccountDTO();
        employeeDTO.setEmail(email);
        employeeDTO.setPassword(password);
        employeeDTO.setAccountType(type);

        //President DTO
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);

        ResponseEntity<Long> out1 = employeeController.registerEmployee(employeeDTO);

        ResponseEntity<EmployeeDTO> out3 = employeeController.getEmployee(out1.getBody(), visitor.getAccountId());

        assertEquals(out3.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testGetEmployeeScheduleSuccess() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        workDay.setStartTime(startTime);
        workDay.setEndTime(endTime);
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());
        List<WorkDayDTO> scheduleDTO = new ArrayList<WorkDayDTO>();
        scheduleDTO.add(workDayDTO);

        employee = employeeRepository.save(employee);
        employee.addSchedule(workDay);
        workDayRepository.save(workDay);


        //check if response and body is correct
        try {
            ResponseEntity response = employeeController.getSchedule(employee.getAccountId(), president.getAccountId());

            List<WorkDayDTO> retrievedSchedule = (List<WorkDayDTO>) response.getBody();
            assertEquals(response.getStatusCode(), HttpStatus.OK);

            assertEquals(retrievedSchedule.get(0).getEndTime(), scheduleDTO.get(0).getEndTime());
            assertEquals(retrievedSchedule.get(0).getDay(), scheduleDTO.get(0).getDay());
        } catch (Exception e) {
        }
    }

    @Test
    public void testGetEmployeeScheduleForbidden() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-10-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());
        List<WorkDayDTO> scheduleDTO = new ArrayList<WorkDayDTO>();
        scheduleDTO.add(workDayDTO);

        employee.addSchedule(workDay);
        employee = employeeRepository.save(employee);


        //check if response and body is correct
        String body = "{day: 2013-09-04}";
        try {
            ResponseEntity response = employeeController.getSchedule(employee.getAccountId(), 123L);

            List<WorkDayDTO> retrievedSchedule = (List<WorkDayDTO>) response.getBody();
            assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);

        } catch (Exception e) {
        }
    }

    @Test
    public void testGetEmployeeScheduleWrongEmployee() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());
        List<WorkDayDTO> scheduleDTO = new ArrayList<WorkDayDTO>();
        scheduleDTO.add(workDayDTO);

        employee.addSchedule(workDay);
        employee = employeeRepository.save(employee);


        //check if response and body is correct
        String body = "{day: 2013-09-04}";
        try {
            ResponseEntity response = employeeController.getSchedule(123L, president.getAccountId());

            List<WorkDayDTO> retrievedSchedule = (List<WorkDayDTO>) response.getBody();
            assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
        }
    }

    @Test
    public void testGetEmployeeWorkDayByDateSuccess() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());

        employee = employeeRepository.save(employee);
        employee.addSchedule(workDay);
        workDayRepository.save(workDay);



        //check if response and body is correct
        String body = "2013-09-04";
        try {
            ResponseEntity response = employeeController.getWorkDayByDate(body, employee.getAccountId(), president.getAccountId());
            List<WorkDayDTO> retrievedDTO = (List<WorkDayDTO>) response.getBody();
            assertEquals(workDayDTO, retrievedDTO);
            assertEquals(response.getStatusCode(), HttpStatus.OK);

        } catch (Exception e) {
        }
    }

    @Test
    public void testGetEmployeeWorkDayByDateWrongDate() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());


        employee = employeeRepository.save(employee);
        employee.addSchedule(workDay);
        workDayRepository.save(workDay);


        //check if response and body is correct
        String body = "2020-09-02";
        try {
            ResponseEntity response = employeeController.getWorkDayByDate(body, employee.getAccountId(), president.getAccountId());
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        } catch (Exception e) {
        }
    }


    @Test
    public void addWorkDaySuccess() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());

        //employee.addSchedule(workDay);
        employee = employeeRepository.save(employee);


        //check if response and body is correct
        String body = "{\"day\": \"2013-09-04\"}";
        try {
            ResponseEntity response = employeeController.addWorkDay(employee.getAccountId(), president.getAccountId(), body);

            WorkDayDTO retrievedSchedule = (WorkDayDTO) response.getBody();
            assertEquals(retrievedSchedule.getEmployeeId(), workDayDTO.getEmployeeId());
            assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (Exception e) {
        }
    }

    @Test
    public void addWorkDayNonExistingEmployee() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());

        employee.addSchedule(workDay);
        employee = employeeRepository.save(employee);


        //check if response and body is correct
        String body = "{\"day\": \"2013-09-04\"}";
        try {
            ResponseEntity response = employeeController.addWorkDay(123L, president.getAccountId(), body);
            assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
        }
    }

    @Test
    public void ModifyWorkDaySuccess() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());

        employee.addSchedule(workDay);
        employee = employeeRepository.save(employee);


        //check if response and body is correct
        String body = "{day: 2013-09-04,startTime:3:00}";
        try {
            ResponseEntity response = employeeController.modifyWorkDay(employee.getAccountId(), president.getAccountId(), body);
            WorkDayDTO retrievedSchedule = (WorkDayDTO) response.getBody();
            assertEquals("3:00", retrievedSchedule.getStartTime());
            assertEquals(response.getStatusCode(), HttpStatus.OK);
        } catch (Exception e) {
        }
    }

    @Test
    public void ModifyWorkDayWrongDay() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());

        employee.addSchedule(workDay);
        employee = employeeRepository.save(employee);


        //check if response and body is correct
        String body = "{day: 2017-10-10,startTime:3:00}";
        try {
            ResponseEntity response = employeeController.modifyWorkDay(employee.getAccountId(), president.getAccountId(), body);
            assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
        }
    }

    @Test
    public void deleteWorkDaySuccess() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());

        employee = employeeRepository.save(employee);
        employee.addSchedule(workDay);
        workDayRepository.save(workDay);



        //check if response and body is correct
        String body = "2013-09-04";
        try {
            ResponseEntity response = employeeController.deleteWorkDay(body, employee.getAccountId(), president.getAccountId());
            assertEquals(response.getStatusCode(), HttpStatus.OK);

        } catch (Exception e) {
        }
    }
    @Test
    public void deleteWorkDayWrongDate() {
        // setup the employee

        President president = new President();
        president = employeeRepository.save(president);

        MyMuseum myMuseum = new MyMuseum();
        Employee employee = new Employee("bob@gmail.com", "password", 12, 200, myMuseum);

        WorkDay workDay = new WorkDay();
        Date day = Date.valueOf("2013-09-04");
        workDay.setDay(day);

        String startTime = "9:00";
        String endTime = "13:00";
        String employeeEmail = employee.getEmail();
        WorkDayDTO workDayDTO = new WorkDayDTO(startTime, endTime, day, employeeEmail, employee.getAccountId());

        employee = employeeRepository.save(employee);
        employee.addSchedule(workDay);
        workDayRepository.save(workDay);



        //check if response and body is correct
        String body = "2013-09-06";
        try {
            ResponseEntity response = employeeController.deleteWorkDay(body, employee.getAccountId(), president.getAccountId());
            assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
        }
    }
}
