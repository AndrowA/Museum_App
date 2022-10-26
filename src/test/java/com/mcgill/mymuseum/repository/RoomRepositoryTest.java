package com.mcgill.mymuseum.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class RoomRepositoryTest {
    @Autowired
    RoomRepository roomRepository;

    @AfterEach
    public void clearDatabase() {
        roomRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadDisplayRoom() {
        //Create Objects

        //Save Object

        //Read Object

        //Assert that object has correct attributes
    }

    @Test
    public void testPersistAndLoadStorageRoom() {
        //Create Objects

        //Save Object

        //Read Object

        //Assert that object has correct attributes
    }
}