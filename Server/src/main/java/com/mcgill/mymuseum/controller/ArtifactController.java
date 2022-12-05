package com.mcgill.mymuseum.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.dto.ArtifactDTO;
import com.mcgill.mymuseum.dto.RoomDTO;
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

import java.util.ArrayList;

@RestController
@RequestMapping("/artifact")
@CrossOrigin(origins = "http://localhost:3000")
public class ArtifactController {
    @Autowired
    ArtifactService artifactService;
    @Autowired
    AccountService accountService;
    @Autowired
    RoomService roomService;

    /**
     * Controller method for retrieving an artifact
     * @param id of artifact
     * @return ResponseEntity of DTO if successful or HTTP status
     */
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

    /**
     * Controller method for retrieving all artifacts
     * @param count how many artifacts to display
     * @param page which page to display
     * @return ResponseEntity of DTO if successful or HTTP status
     */
    @GetMapping("/all/{page}/{count}")
    public ResponseEntity getManyArtifacts(@PathVariable(name="count") int count, @PathVariable(name="page") int page) {
        try{
            ArrayList<Artifact> artifactArrayList = (ArrayList<Artifact>) artifactService.getManyArtifacts(count,page);
            ArrayList<ArtifactDTO> dtos = new ArrayList<>();
            for(Artifact artifact : artifactArrayList ){
                dtos.add(new ArtifactDTO(artifact));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Controller method to add an atrifact
     * @param rid id of requester
     * @param body body of the post request
     * @return ResponseEntity of artifact if successful or HTTP status
     */
    @PostMapping("/add") // to create an artifact
    public ResponseEntity addArtifact(@RequestParam("token") long rid,@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Artifact newArtifact = mapper.readValue(body, Artifact.class);
            if(!accountService.authenticate(rid, AccountService.TargetType.ARTIFACT, AccountService.Action.MODIFY)){
                return new ResponseEntity<>("Invalid Permissions!",HttpStatus.FORBIDDEN);
            }
            Artifact savedArtifact = artifactService.saveArtifact(newArtifact);
            return new ResponseEntity<>(savedArtifact, HttpStatus.CREATED); // all good
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Controller method to modify an artifact
     * @param rid requester's id
     * @param id id of the artifact to be modified
     * @param body body of the post request
     * @return ResponseEntity of artifact if successful or HTTP status
     */
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
            Artifact savedArtifact = artifactService.saveArtifact(newArtifact);
            return new ResponseEntity<>(savedArtifact, HttpStatus.CREATED); // all good
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method to assign an artifact to a room
     * @param rid requester's id
     * @param id of artifact
     * @param roomId
     * @return ResponseEntity if successful or HTTP status
     */
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
            Artifact savedArtifact = artifactService.saveArtifact(artifact);
            ArtifactDTO dto = new ArtifactDTO(savedArtifact);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Controller method to get room fo an artifact
     * @param id of artifact
     * @return ResponseEntity of DTO if successful or HTTP status
     */
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

    /**
     * Controller method to get room for an artifact
     * @param name of artifact
     * @return ResponseEntity of DTO if successful or HTTP status
     */
    @GetMapping("/room/info/byname/{name}")
    public ResponseEntity getRoomByName (@PathVariable(name="name") String name){
        try{
            Room room = roomService.retrieveRoomByName(name);
            RoomDTO dto = new RoomDTO(room);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/delete/{id}")
    public ResponseEntity deleteArtifact(@RequestParam("token") long rid, @PathVariable(name="id") long id){
        try {
            if(!accountService.authenticate(rid, AccountService.TargetType.ARTIFACT, AccountService.Action.MODIFY)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            ArtifactDTO artifact = new ArtifactDTO(artifactService.retrieveArtifact(id));
            if (artifactService.deleteArtifact(id)) {
                return new ResponseEntity<>(artifact, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("artifact not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
