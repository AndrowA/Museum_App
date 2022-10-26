package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.DisplayRoom;
import com.mcgill.mymuseum.model.WorkDay;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
        int roomCapacity = 100;
        DisplayRoom displayRoom1 = new DisplayRoom();
        displayRoom1.setRoomCapacity(roomCapacity);

        //Save Object
        displayRoom1 = roomRepository.save(displayRoom1);
        Long id = displayRoom1.getRoomId();

        //Read Object
        displayRoom1 = (DisplayRoom) roomRepository.findById(id).get();

        //Assert that object has correct attributes
        assertNotNull(displayRoom1);
        assertEquals(roomCapacity,displayRoom1.getRoomCapacity());
    }

    @Test
    public void testPersistAndLoadStorageRoom() {
        //Create Objects

        //Save Object

        //Read Object

        //Assert that object has correct attributes
    }
}