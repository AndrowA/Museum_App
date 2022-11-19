package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.DisplayRoom;
import com.mcgill.mymuseum.model.Room;
import com.mcgill.mymuseum.repository.ArtifactRepository;
import com.mcgill.mymuseum.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    ArtifactRepository artifactRepository;

    public Room retrieveRoom(long id) throws Exception{
        Optional<Room> room = roomRepository.findById(id);
        if(room.isEmpty()){
            throw new Exception();
        }else {
            return room.get();
        }
    }

    public Room retrieveRoomByArtifactId(long artifactId) throws Exception{
        Optional<Artifact> artifact = artifactRepository.findById(artifactId);

        if(artifact.isEmpty()){
            throw new Exception();
        }else {
            return artifact.get().getRoom();
        }
    }

    public Room saveRoom(Room room){
        return roomRepository.save(room);
    }
}
