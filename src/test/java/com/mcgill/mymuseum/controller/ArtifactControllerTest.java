package com.mcgill.mymuseum.controller;

import com.mcgill.mymuseum.dto.ArtifactDTO;
import com.mcgill.mymuseum.dto.RoomDTO;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.DisplayRoom;
import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.President;
import com.mcgill.mymuseum.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Tag("integration")
class ArtifactControllerTest {

    @Autowired
    ArtifactController artifactController;
    @Autowired
    ArtifactRepository artifactRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    MyMuseumRepository museumRepository;
    @Autowired
    AccountRepository accountRepository;

    DisplayRoom room;
    Artifact artifact;
    President president;
    Artifact artifact2;

    @BeforeEach
    void setUp() {
        clearDB();
        President president = new President();
        this.president = accountRepository.save(president);
        DisplayRoom room = new DisplayRoom();
        room.setRoomCapacity(400);
        room.setName("room1");
        Artifact artifact = new Artifact();
        Artifact artifact2 = new Artifact();
        artifact.setUrl("http://");
        artifact.setDescription("hello");
        artifact.setName("artifact1");
        artifact2.setUrl("http://htht");
        artifact2.setDescription("hello2");
        artifact2.setName("artifact2");
        this.room = roomRepository.save(room);
        artifact.setRoom(room);
        this.artifact = artifactRepository.save(artifact);
        this.artifact2 = artifactRepository.save(artifact2);
    }

    @AfterEach
    void tearDown(){
        clearDB();
    }

    @Test
    void getArtifactSuccess() {
        ResponseEntity result = artifactController.getArtifact(artifact.getArtifactId());
        ArtifactDTO returned = (ArtifactDTO) result.getBody();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(this.artifact.getArtifactId(), returned.id);//enough for now
    }

    @Test
    void getArtifactFail() {
        ResponseEntity result = artifactController.getArtifact(0l);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getManyArtifactsSucceed() {
        ResponseEntity result = artifactController.getManyArtifacts(50,1);
        ArrayList<ArtifactDTO> returned = (ArrayList<ArtifactDTO>) result.getBody();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(this.artifact.getArtifactId(), returned.get(0).id);
    }

    @Test
    void getManyArtifactsFail() {
        artifactRepository.deleteAll();
        ResponseEntity result = artifactController.getManyArtifacts(50,1);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,result.getStatusCode());
    }
    @Test
    void addArtifactSucceed() {
        ResponseEntity result = artifactController.addArtifact(this.president.getAccountId(),"{\n" +
                "    \"name\": \"tut's skull\",\n" +
                "    \"description\": \"skull obtained from tut\",\n" +
                "    \"url\": \"http://foofle\"\n" +
                "}"
        );
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals( ((Artifact)result.getBody()).getName(), "tut's skull");
    }

    @Test
    void addArtifactFail() {
        ResponseEntity result = artifactController.addArtifact(this.president.getAccountId(),"");
        assertEquals(result.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void modifyArtifactSucceed() {
        ResponseEntity result = artifactController.modifyArtifact(this.president.getAccountId(), this.artifact.getArtifactId(), "{\n" +
                "    \"name\": \"tuttttt's skull\",\n" +
                "    \"description\": \"skull obtained from tut\",\n" +
                "    \"url\": \"http://foofle\"\n" +
                "}");
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        assertEquals( ((Artifact)result.getBody()).getName(), "tuttttt's skull");
    }
    @Test
    void modifyArtifactNoPermission() {
        ResponseEntity result = artifactController.modifyArtifact(33l, this.artifact.getArtifactId(), "{\n" +
                "    \"name\": \"tuttttt's skull\",\n" +
                "    \"description\": \"skull obtained from tut\",\n" +
                "    \"url\": \"http://foofle\"\n" +
                "}");

        assertEquals(result.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void assignRoomSucceed() {
        ResponseEntity result = artifactController.assignRoom(this.president.getAccountId(),this.artifact2.getArtifactId(),this.room.getRoomId());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals( ((ArtifactDTO)result.getBody()).roomId, this.room.getRoomId());
    }

    @Test
    void assignRoomThatDoesNotExist() {
        ResponseEntity result = artifactController.assignRoom(this.president.getAccountId(),this.artifact.getArtifactId(),22l);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getRoomSucceed() {
        ResponseEntity result = artifactController.getRoom(this.artifact.getArtifactId());
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals( ((RoomDTO)result.getBody()).id,this.artifact.getRoom().getRoomId() );
    }

    @Test
    void getRoomFail() {
        ResponseEntity result = artifactController.getRoom(55l);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    private void clearDB(){
        accountRepository.deleteAll();
        artifactRepository.deleteAll();
        loanRepository.deleteAll();
        roomRepository.deleteAll();
    }
}