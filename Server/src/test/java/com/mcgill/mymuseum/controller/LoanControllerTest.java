package com.mcgill.mymuseum.controller;

import com.mcgill.mymuseum.dto.ArtifactDTO;
import com.mcgill.mymuseum.dto.LoanDTO;
import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.*;
import com.mcgill.mymuseum.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoanControllerTest {
    @Autowired
    ArtifactController artifactController;
    @Autowired
    LoanController loanController;
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
    Loan loan;
    Loan loan2;

    @BeforeEach
    void setUp() {
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

        Loan loan1 = new Loan();
        Loan loan2 = new Loan();
        Date startDate = Date.valueOf("2022-10-10");
        Date endDate = Date.valueOf("2022-11-11");
        loan1.setStartDate(startDate);
        loan1.setEndDate(endDate);
        Visitor visitor1 = new Visitor();
        visitor1.setEmail("johnnytest@gmail.com");
        visitor1.setPassword("password");
        loan1.setLoanee(visitor1);
        loan1.setArtifact(artifact);
        this.loan = loanRepository.save(loan1);
        this.loan2 = loanRepository.save(loan2);
    }

    @AfterEach
    void tearDown(){
        clearDB();
    }

    @Test
    void getLoanSuccess() throws MuseumException {
        ResponseEntity result = loanController.getLoan(loan.getLoanId());
        LoanDTO returned = (LoanDTO) result.getBody();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertEquals(this.loan.getLoanId(), returned.getLoanId());
    }

    private void clearDB(){
        loanRepository.deleteAll();
        accountRepository.deleteAll();
        artifactRepository.deleteAll();
        roomRepository.deleteAll();
        museumRepository.deleteAll();
    }
}
