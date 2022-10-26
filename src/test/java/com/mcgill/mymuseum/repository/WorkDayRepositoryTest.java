package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.WorkDay;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

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
        String workStartTime = "13:00";
        String workEndTime = "20:00";
        Date workDate = new Date(2022,10, 31);
        WorkDay workDay1 = new WorkDay();
        workDay1.setStartTime(workStartTime);
        workDay1.setEndTime(workEndTime);
        workDay1.setDay(workDate);

        //Save Object
        workDay1 = workDayRepository.save(workDay1);
        Long id = workDay1.getId();

        //Read Object
        workDay1 = workDayRepository.findById(id).get();

        //Assert that object has correct attributes
        assertNotNull(workDay1);
        assertEquals(workStartTime,workDay1.getStartTime());
        assertEquals(workEndTime,workDay1.getEndTime());
        assertEquals(workDate,workDay1.getDay());
    }
}