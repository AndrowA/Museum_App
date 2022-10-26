package com.mcgill.mymuseum.repository;

import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkDayRepositoryTest {
    @Autowired
    WorkDayRepository workDayRepository;

    @AfterEach
    public void clearDatabase() {
        workDayRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadWorkDay() {
        //Create Objects

        //Save Object

        //Read Object

        //Assert that object has correct attributes
    }
}