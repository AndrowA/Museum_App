package com.mcgill.mymuseum.repository;


import com.mcgill.mymuseum.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MyMuseumRepositoryTest {

    @Autowired
    MyMuseumRepository myMuseumRepository;
    @Autowired
    AccountRepository presidentRepository;
    @Autowired
    RoomRepository roomRepository;

    @AfterEach
    public void clearDatabase() { myMuseumRepository.deleteAll(); presidentRepository.deleteAll(); roomRepository.deleteAll();}

    @Test
    public void testPersistAndLoadMyMuseum() {
        //Create Object
        String price = "10";
        String address = "White House";
        President president = new President();
        StorageRoom storageRoom = new StorageRoom();
        //Create Object
        MyMuseum myMuseum = new MyMuseum(price,address, president,storageRoom);
        //Write object & references
        presidentRepository.save(myMuseum.getPresident());
        roomRepository.save(myMuseum.getStorageRoom());
        myMuseum = myMuseumRepository.save(myMuseum);
        Long id = myMuseum.getMuseumId();

        //Read Object
        myMuseum = myMuseumRepository.findById(id).get();


        //Assert that object has correct attributes
        assertNotNull(myMuseum);
        assertEquals(price,myMuseum.getPricePerPass());
    }

}
