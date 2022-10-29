package com.mcgill.mymuseum.repository;


import com.mcgill.mymuseum.model.*;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MyMuseumRepositoryTest {

    @Autowired
    MyMuseumRepository myMuseumRepository;

    @AfterEach
    public void clearDatabase() { myMuseumRepository.deleteAll(); }

    @Test
    public void testPersistAndLoadMyMuseum() {
        String price = "10";
        String address = "White House";
        String email = "Joe_Biden@whitehouse.com";
        String password = "password";
        double hourlyWage = 200;
        double overTimeHourlyWage = 300;
        String storageName = "Storage A";
        //Create Object
        MyMuseum myMuseum = new MyMuseum(price,
                address,
                email,
                password,
                hourlyWage,
                overTimeHourlyWage,
                storageName);

        // Add Associations
        Loan loan = new Loan();
        Visitor visitor = new Visitor();
        Employee employee = new Employee();
        WorkDay workDay = new WorkDay();
        WorkDay openingHour = new WorkDay();
        MuseumPass museumPass = new MuseumPass();
        Artifact artifact = new Artifact();
        DisplayRoom displayRoom = new DisplayRoom();
        StorageRoom storageRoom = myMuseum.getStorageRoom();
        President president = myMuseum.getPresident();
        myMuseum.addLoan(loan);
        myMuseum.addVisitor(visitor);
        myMuseum.addEmployee(employee);
        myMuseum.addWorkday(workDay);
        myMuseum.addOpeningHour(openingHour);
        myMuseum.addArtifact(artifact);
        myMuseum.addDisplayRoom(displayRoom);
        myMuseum.addMuseumPass(museumPass);


        //Save Object
        myMuseum = myMuseumRepository.save(myMuseum);
        Long id = myMuseum.getMuseumId();

        //Read Object
        myMuseum = myMuseumRepository.findById(id).get();

        assertNotNull(myMuseum);
        assertInstanceOf(artifact.getClass(),myMuseum.getArtifact(0));
        assertInstanceOf(museumPass.getClass(),myMuseum.getMuseumPass(0));
        assertInstanceOf(loan.getClass(),myMuseum.getLoan(0));
        assertInstanceOf(employee.getClass(),myMuseum.getEmployee(0));
        assertInstanceOf(displayRoom.getClass(),myMuseum.getDisplayRoom(0));
        assertInstanceOf(storageRoom.getClass(),myMuseum.getStorageRoom());
        assertInstanceOf(president.getClass(), myMuseum.getPresident());
        assertInstanceOf(openingHour.getClass(),myMuseum.getOpeningHour(0));
        assertInstanceOf(workDay.getClass(),myMuseum.getWorkday(0));
        assertInstanceOf(visitor.getClass(),myMuseum.getVisitor(0));

        //Assert that object has correct attributes
        assertEquals(price,myMuseum.getPricePerPass());
        assertEquals(address,myMuseum.getAddress());

        //Assert that object has correct associations

    }

}
