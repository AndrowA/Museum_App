package com.mcgill.mymuseum.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.dto.ArtifactDTO;
import com.mcgill.mymuseum.dto.RoomDTO;
import com.mcgill.mymuseum.model.Account;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.DisplayRoom;
import com.mcgill.mymuseum.model.Room;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.ArtifactService;
import com.mcgill.mymuseum.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {
    @Autowired
    ArtifactService artifactService;
    @Autowired
    AccountService accountService;
    @Autowired
    RoomService roomService;
    @GetMapping("/get/{id}") //done
    public ResponseEntity getArtifact(@PathVariable(name="id") long id) {
        try {
            Artifact artifact = artifactService.retrieveArtifact(id);
            ArtifactDTO dto = new ArtifactDTO(artifact);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Artifact id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all/{page}/{count}") //will do later
    public ResponseEntity getManyArtifacts(@PathVariable(name="count") int count, @PathVariable(name="page") int page){
        return new ResponseEntity("path variables are count: " + count + ", page: " + page, HttpStatus.OK);
    }

    @PostMapping("/add") // to create an artifact
    public ResponseEntity addArtifact(@RequestParam("token") long rid,@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Artifact newArtifact = mapper.readValue(body, Artifact.class);
            if(!accountService.authenticate(rid, AccountService.TargetType.ARTIFACT, AccountService.Action.MODIFY)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Artifact savedArtifact = artifactService.saveArtiact(newArtifact);
            return new ResponseEntity<>(savedArtifact, HttpStatus.CREATED); // all good
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/modify/{id}")
    public ResponseEntity modifyArtifact(@RequestParam("token") long rid, @PathVariable(name="id") long id, @RequestBody String body){
        //should check for permission to create
        ObjectMapper mapper = new ObjectMapper();
        try {
            // map the json body sent by the request to the artifact class
            Artifact newArtifact = mapper.readValue(body, Artifact.class);
            if(!accountService.authenticate(rid, AccountService.TargetType.ARTIFACT, AccountService.Action.MODIFY)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Artifact toModify = artifactService.retrieveArtifact(id);
            newArtifact.setArtifactId(toModify.getArtifactId());
            Artifact savedArtifact = artifactService.saveArtiact(newArtifact);
            return new ResponseEntity<>(savedArtifact, HttpStatus.CREATED); // all good
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/assign/room/{id}/{roomId}")
    public ResponseEntity assignRoom(@RequestParam("token") long rid, @PathVariable(name="id") long id,@PathVariable(name="roomId") long roomId) {
        try {
            Room room = roomService.retrieveRoom(roomId);
            Artifact artifact = artifactService.retrieveArtifact(id);
            if(!accountService.authenticate(rid, AccountService.TargetType.ARTIFACT, AccountService.Action.MODIFY)){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if(room instanceof DisplayRoom && ((DisplayRoom) room).getRoomCapacity()>=500){
                return new ResponseEntity("Display Room is full", HttpStatus.FORBIDDEN);
            }
            artifact.setRoom(room);
            if(room instanceof DisplayRoom){
                int cap = ((DisplayRoom) room).getRoomCapacity();
                ((DisplayRoom) room).setRoomCapacity(++cap);
                roomService.saveRoom(room);
            }
            Artifact savedArtifact = artifactService.saveArtiact(artifact);
            ArtifactDTO dto = new ArtifactDTO(savedArtifact);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/assign/room/info/{id}")
    public ResponseEntity getRoom(@PathVariable(name="id") long id){
        try{
            Room room = roomService.retrieveRoomByArtifactId(id);
            RoomDTO dto = new RoomDTO(room);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
