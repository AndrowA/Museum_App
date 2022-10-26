package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.MuseumPass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MuseumPassRepositoryTest {
    @Autowired
    MuseumPassRepository museumPassRepository;

    @AfterEach
    public void clearDatabase() {
        museumPassRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadMuseumPass() {
        //Create Objects
        int passCost = 10;
        Date passDate = new Date(2022,10, 31);
        MuseumPass pass = new MuseumPass();
        pass.setPassCost(passCost);
        pass.setPassDate(passDate);

        //Save Object
        pass = museumPassRepository.save(pass);
        Long id = pass.getPassId();

        //Read Object
        pass = museumPassRepository.findById(id).get();

        //Assert that object has correct attributes
        assertNotNull(pass);
        assertEquals(passCost,pass.getPassCost());
        assertEquals(passDate,pass.getPassDate());

    }
}