package com.mcgill.mymuseum.controller;

import com.mcgill.mymuseum.dto.MuseumPassDTO;
import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import com.mcgill.mymuseum.service.MuseumPassService;
import com.mcgill.mymuseum.service.VisitorService;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@Tag("integration")
@SpringBootTest
public class MuseumPassControllerTest {
    @Autowired
     MuseumPassController passController;
    @Autowired
     MuseumPassRepository passRepository;
    @Autowired
     VisitorService visitorService;
    @Autowired
     MuseumPassService passService;
    @Autowired
     AccountRepository accountRepository;
    @AfterEach
    public void clearDatabase(){
        passRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateMuseumPassValid() {
        // example visitor creation
        String passDate = "{ \"passDate\" : \"2022-011-27\" }";
        String expectedPassDate = "2022-11-27";
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Long visitorID = visitor.getAccountId();
        ResponseEntity<MuseumPassDTO> out =  passController.buyMuseumPass(passDate,visitorID.toString()); //call controller to create pass for Visitor
        System.out.println(out.getStatusCode());
        assertTrue(out.getStatusCode().equals(HttpStatus.OK)); //make sure pass is created
        assertEquals(out.getBody().getaPassDate().toString(), expectedPassDate); //verify if info is correct

    }

    @Test
    public void testCreateMuseumPassInvalid() {
        // account DTO
        String passDate = "{ \"passDate\" : \"2022-011-27\" }";
        Employee employee = new Employee();
        employee = accountRepository.save(employee);
        Long id = employee.getAccountId();
        ResponseEntity<MuseumPassDTO> out =  passController.buyMuseumPass(passDate,id.toString());
        assertTrue(out.getStatusCode().equals(HttpStatus.FORBIDDEN)); //make sure employee cannot create pass

    }


    @Test
    public void testGetMuseumPassValid() {
        //Create mock visitor
        String passDate = "{ \"passDate\" : \"2022-011-27\" }";
        String expectedPassDate = "2022-11-27";
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Long id = visitor.getAccountId();
        ResponseEntity<MuseumPassDTO> out =  passController.buyMuseumPass(passDate,id.toString());
        ResponseEntity<MuseumPassDTO> out2 = passController.getMuseumPass(out.getBody().getPassId().toString()); //get visitor's pass from pass ID
        assertEquals(out2.getStatusCode(),HttpStatus.OK); //make sure GET was sent through Request
        assertEquals(out2.getBody().getaPassDate().toString(), expectedPassDate); //make sure values match

    }
    @Test
    public void testGetMuseumPassInvalid() {
        //Create mock visitor
        String passDate = "{ \"passDate\" : \"2022-011-27\" }";
        String expectedPassDate = "2020-11-27";
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Long id = visitor.getAccountId();
        ResponseEntity<MuseumPassDTO> out =  passController.buyMuseumPass(passDate,id.toString());
        ResponseEntity<MuseumPassDTO> out2 = passController.getMuseumPass(out.getBody().getPassId().toString()); //get visitor pass from id
        assertEquals(out2.getStatusCode(),HttpStatus.OK);
        assertNotEquals(out2.getBody().getaPassDate().toString(), expectedPassDate);//values do not match up

    }
    @Test
    public void testAllGetMuseumPassesValid() {
        String passDate = "{ \"passDate\" : \"2022-011-27\" }";
        String expectedPassDate = "2022-11-27";
        Visitor visitor = new Visitor();
        Visitor visitor2 = new Visitor();
        Employee employee = new Employee();
        visitor = accountRepository.save(visitor);
        visitor2 = accountRepository.save(visitor2);
        employee = accountRepository.save(employee);
        Long id = visitor.getAccountId();
        Long id2 = visitor2.getAccountId();
        Long id3 = employee.getAccountId();
        ResponseEntity<MuseumPassDTO> out =  passController.buyMuseumPass(passDate,id.toString());
        ResponseEntity<MuseumPassDTO> out2 =  passController.buyMuseumPass(passDate,id2.toString());
        ResponseEntity<MuseumPassDTO> out3 = passController.getAllMuseumPasses(id3.toString()); //get visitor's pass from pass ID
        assertEquals(out3.getStatusCode(),HttpStatus.OK); //make sure GET was sent through Request
        assertEquals(out.getBody().getaPassDate().toString(), expectedPassDate); //make sure values match
        assertEquals(out2.getBody().getaPassDate().toString(), expectedPassDate); //make sure values match

    }
    @Test
    public void testAllGetMuseumPassesInvalid() {
        String passDate = "{ \"passDate\" : \"2022-011-27\" }";
        String expectedPassDate = "2022-10-27";
        Visitor visitor = new Visitor();
        Visitor visitor2 = new Visitor();
        Employee employee = new Employee();
        visitor = accountRepository.save(visitor);
        visitor2 = accountRepository.save(visitor2);
        employee = accountRepository.save(employee);
        Long id = visitor.getAccountId();
        Long id2 = visitor2.getAccountId();
        Long id3 = employee.getAccountId();
        ResponseEntity<MuseumPassDTO> out =  passController.buyMuseumPass(passDate,id.toString());
        ResponseEntity<MuseumPassDTO> out2 =  passController.buyMuseumPass(passDate,id.toString());
        ResponseEntity<MuseumPassDTO> out3 = passController.getAllMuseumPasses(id3.toString()); //get visitor's pass from ID
        assertEquals(out3.getStatusCode(),HttpStatus.OK);
        assertNotEquals(out2.getBody().getaPassDate().toString(), expectedPassDate);//values do not match up
    }
    @Test
    public void testGetVisitorPassesValid() throws MuseumException {
        String passDate = "{ \"passDate\" : \"2022-011-27\" }";
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Long id = visitor.getAccountId();
        passController.buyMuseumPass(passDate,id.toString());
        ResponseEntity<MuseumPassDTO> out2 = passController.getVisitorPasses(id); //get visitor's pass from visitor ID
        assertEquals(out2.getStatusCode(),HttpStatus.OK); //make sure GET was sent through Request
    }
    @Test
    public void testGetVisitorPassesInvalid() throws MuseumException {
        String passDate = "{ \"passDate\" : \"2022-011-27\" }";
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Employee employee = new Employee();
        employee = accountRepository.save(employee);
        Long id = visitor.getAccountId();
        Long id2 = employee.getAccountId();
        ResponseEntity<MuseumPassDTO> out =  passController.buyMuseumPass(passDate,id2.toString());
        try {
            ResponseEntity<MuseumPassDTO> out2 = passController.getVisitorPasses(id2); //get visitor's pass from visitor ID
            assertNotEquals(out2.getStatusCode(),HttpStatus.OK); //make sure GET was sent through Request
        }  catch (Exception ignore) {
        }
    }
}