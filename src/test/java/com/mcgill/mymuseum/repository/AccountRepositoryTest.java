package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    public void clearDatabase() {
        accountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadEmployeeAccount() {
        //Create Objects
        String username = "yassine.meliani@gmail.com";
        String password = "password";
        Employee employee1 = new Employee(username,password,40,80,null);

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
}