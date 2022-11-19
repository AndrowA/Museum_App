package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanRepositoryTest {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ArtifactRepository artifactRepository;
    @Autowired
    RoomRepository roomRepository;

    @BeforeEach
    public void clearDatabase() {
        artifactRepository.deleteAll();
        loanRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadLoan() {

        //create default attributes
        Date startDate = Date.valueOf("2015-03-31");
        Date endDate = Date.valueOf("2019-03-31");
        Loan.LoanStatus status = Loan.LoanStatus.Approved;

        //Create Object and set attributes
        Loan loan1 = new Loan();
        loan1.setLoanStatus(status);
        loan1.setStartDate(startDate);
        loan1.setEndDate(endDate);

        //Save Object
        loanRepository.save(loan1);

        //Read Object
        long id = loan1.getLoanId();
        loan1 = (Loan) loanRepository.findById(id).get();

        //Assert that object has correct attributes
        assertNotNull(loan1);
        assertEquals(startDate,loan1.getStartDate());
        assertEquals(endDate,loan1.getEndDate());
        assertEquals(endDate,loan1.getEndDate());
        assertEquals(status,loan1.getLoanStatus());
    }

    @Test
    public void testPersistAndLoadLoan2() {
        //Create Objects
        String artifactName = "Golden mask of Tut";
        String artifactDescription = "the most famous and admired artifacts of ancient Egypt in history and the world. It is the funerary death mask of the Egyptian pharaoh King Tutankamun.";
        String artifactPictureURL="www.maskOfTut.com";
        Artifact artifact1 = new Artifact();
        artifact1.setName(artifactName);
        artifact1.setDescription(artifactDescription);
        artifact1.setUrl(artifactPictureURL);

        //create default attributes
        Date startDate = Date.valueOf("2015-03-31");
        Date endDate = Date.valueOf("2019-03-31");
        Loan.LoanStatus status = Loan.LoanStatus.Approved;

        //Create Object and set attributes
        Loan loan1 = new Loan();
        loan1.setLoanStatus(status);
        loan1.setStartDate(startDate);
        loan1.setEndDate(endDate);

        artifact1.setLoan(loan1);
        Room room1 = new DisplayRoom();
        artifact1.setRoom(room1);


        //Save Object

        loan1 = loanRepository.save(loan1);
        Long id2 = loan1.getLoanId();
        artifact1 = artifactRepository.save(artifact1);
        room1 = roomRepository.save(room1);
        Long id = artifact1.getArtifactId();

        //Read Object
        artifact1 = artifactRepository.findById(id).get();

        //Assert that object has correct attributes
        assertNotNull(artifact1);
        assertEquals(artifact1.getArtifactId(), id);
        assertEquals(artifact1.getLoan().getLoanId(),id2);
        assertEquals(artifactName,artifact1.getName());
        assertEquals(artifactDescription,artifact1.getDescription());
        assertEquals(artifactPictureURL, artifact1.getUrl());
    }
}
