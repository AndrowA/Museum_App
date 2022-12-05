package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.DisplayRoom;
import com.mcgill.mymuseum.model.Room;
import com.mcgill.mymuseum.model.StorageRoom;
import com.mcgill.mymuseum.repository.ArtifactRepository;
import com.mcgill.mymuseum.repository.LoanRepository;
import com.mcgill.mymuseum.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ArtifactRepository artifactRepository;

    @Transactional
    public Room retrieveRoom(long id) throws Exception {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            throw new Exception();
        } else {
            return room.get();
        }
    }

    @Transactional
    public Room retrieveRoomByName(String name) throws Exception {
        Iterable<Room> rooms = roomRepository.findAll();

        for (Room room : rooms) {

            if (room.getName().equals(name)){
             return room;
            }
        }

        throw new Exception("Room not found!");
    }

    @Transactional
    public Iterable<Room> setupRooms() {
        ArrayList<Room> rooms = (ArrayList<Room>) roomRepository.findAll();
        if (rooms.size() != 10) {
            loanRepository.deleteAll();
            artifactRepository.deleteAll();
            roomRepository.deleteAll();
            // create room
            StorageRoom room = new StorageRoom();
            room.setName("Room 1");
            rooms.add(room);
            for (int i = 2; i <= 9; i++) {
                DisplayRoom room1 = new DisplayRoom();
                room1.setName("Room " + i);
                rooms.add(room1);
            }
            StorageRoom room2 = new StorageRoom();
            room2.setName("Room 10");
            rooms.add(room2);
            roomRepository.saveAll(rooms);
        }
        return rooms;
    }

    @Transactional
    public Room retrieveRoomByArtifactId(long artifactId) throws Exception {
        Optional<Artifact> artifact = artifactRepository.findById(artifactId);

        if (artifact.isEmpty()) {
            throw new Exception();
        } else {
            return artifact.get().getRoom();
        }
    }

    @Transactional
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }
}
