package com.mcgill.mymuseum.repository;


import com.mcgill.mymuseum.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class MyMuseumRepositoryTest {

    @Autowired
    MyMuseumRepository myMuseumRepository;

    @AfterEach
    public void clearDatabase() { myMuseumRepository.deleteAll(); }

    @Test
    public void testPersistAndLoadMyMuseum() {
        //Create Object
        MyMuseum myMuseum = new MyMuseum();

        //Save Object
        myMuseum = myMuseumRepository.save(myMuseum);
        Long id = myMuseum.getMuseumId();

        //Read Object
        myMuseum = myMuseumRepository.findById(id).get();
        //Assert that object has correct attributes
        assertNotNull(myMuseum);
    }

}
