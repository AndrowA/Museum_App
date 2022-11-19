package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.model.Visitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    //@AfterEach
    public void clearDatabase() {
        accountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadEmployeeAccount() {
        //Create Objects
        String username = "yassine.meliani@gmail.com";
        String password = "password";
        MyMuseum myMuseum = new MyMuseum();
        Employee employee1 = new Employee(username,password,40,80,myMuseum);

        //Save Object
        employee1 = accountRepository.save(employee1);
        Long id = employee1.getAccountId();

        //Read Object
        employee1 = (Employee) accountRepository.findById(id).get();

        //Assert that object has correct attributes
        assertNotNull(employee1);
        assertEquals(username,employee1.getEmail());
        assertEquals(password,employee1.getPassword());
    }

    @Test
    public void testPersistAndLoadVisitorAccount() {
        //Create Objects
        String username = "radu.petrescu@mail.mcgill.ca";
        String password = "password";
        Visitor visitor1 = new Visitor();
        visitor1.setEmail(username);
        visitor1.setPassword(password);

        //Save Object
        visitor1 = accountRepository.save(visitor1);
        Long id = visitor1.getAccountId();
        //Read Object
        visitor1 = (Visitor) accountRepository.findById(id).get();
        //Assert that object has correct attributes
        assertNotNull(visitor1);
        assertEquals(username,visitor1.getEmail());
        assertEquals(password,visitor1.getPassword());
    }

    @Test
    public void testPersistAndLoadPresidentAccount() {
        //Create Objects
        String username = "Joe_Biden@WhiteHouse.usa";
        String password = "password";
        President president = new President();
        president.setEmail(username);
        president.setPassword(password);
        president.setHourlyWage(100);
        president.setOverTimeHourlyWage(140);
        //Save Object
        president = accountRepository.save(president);
        Long id = president.getAccountId();
        //Read Object
        president = (President) accountRepository.findById(id).get();
        //Assert that object has correct attributes
        assertNotNull(president);
        assertEquals(username,president.getEmail());
        assertEquals(password,president.getPassword());
    }

}