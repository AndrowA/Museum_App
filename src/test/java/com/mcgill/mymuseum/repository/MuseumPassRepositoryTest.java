package com.mcgill.mymuseum.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        //Save Object

        //Read Object

        //Assert that object has correct attributes
    }
}