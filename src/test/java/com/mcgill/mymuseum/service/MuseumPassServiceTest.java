package com.mcgill.mymuseum.service;
import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class MuseumPassServiceTest {
    @Autowired
     MuseumPassRepository passRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    MuseumPassService museumPassService;
    @Autowired
    AccountService accountService;
    @Autowired
    VisitorService visitorService;
    @BeforeEach
    public void clearDatabase(){
        passRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testGetPassById() {
        MuseumPass museumPass = new MuseumPass();
        museumPass = passRepository.save(museumPass);
        Long museumPassId = museumPass.getPassId();
        assertEquals(museumPass.getPassId(), museumPassService.retrieveMuseumPass(museumPassId).getPassId());
    }

    @Test
    public void testCreatePass() {
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Long visitorID = visitor.getAccountId();
        MuseumPass museumPass = new MuseumPass();
        museumPass = passRepository.save(museumPass);
        Long museumPassId = museumPass.getPassId();
        MuseumPass PassTest = museumPassService.createPass(museumPass, Math.toIntExact(visitorID));
        assertEquals(PassTest.getOwner().getAccountId(), museumPassService.retrieveMuseumPass(museumPassId).getOwner().getAccountId());
        assertEquals(PassTest.getOwner().getAccountId(), visitorID);
    }
    @Test
    public void testEmployeeCantCreatePass() {
        Employee employee = new Employee();
        employee = accountRepository.save(employee);
        Long employeeId = employee.getAccountId();
        try {
            MuseumPass museumPass = new MuseumPass();
            museumPass = passRepository.save(museumPass);
            museumPassService.createPass(museumPass, Math.toIntExact(employeeId));
        }
        catch(NullPointerException e){
            assertEquals("visitor with id "+employeeId +" does not exist", e.getMessage());
        }
    }
    @Test
    public void testPresidentCantCreatePass() {
        President president = new President();
        president = accountRepository.save(president);
        Long presidentAccountId = president.getAccountId();
        try {
            MuseumPass museumPass = new MuseumPass();
            museumPass = passRepository.save(museumPass);
            museumPassService.createPass(museumPass, Math.toIntExact(presidentAccountId));
        }
        catch(NullPointerException e){
            assertEquals("visitor with id "+ presidentAccountId +" does not exist", e.getMessage());
        }
    }
    @Test
    public void testDeletePass() {
        MuseumPass museumPass = new MuseumPass();
        museumPass = passRepository.save(museumPass);
        museumPass.delete();
        assertNull(museumPass.getOwner());
    }


    }

