package com.mcgill.mymuseum.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.dto.LoanDTO;
import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.ArtifactService;
import com.mcgill.mymuseum.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class LoanController {
    @Autowired
    LoanService loanService;
    @Autowired
    AccountService accountService;
    @Autowired
    ArtifactService artifactService;

    @GetMapping("/loan/{id}")
    public ResponseEntity getLoan(@PathVariable long id) throws MuseumException {
        try {
            Loan loan = loanService.retrieveLoanById(id);
            LoanDTO loanDTO = new LoanDTO(loan.getStartDate(),loan.getEndDate(),loan.getLoanStatus(),loan.getLoanee().getEmail(),loan.getArtifact().getName(),loan.getArtifact().getArtifactId(),loan.getMyMuseum().getAddress());
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createLoanRequest/{visitorId}/{artifactId}")
    public ResponseEntity createRequest(@RequestBody String body, @PathVariable Long artifactId, @PathVariable Long visitorId) throws MuseumException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Visitor loanee = (Visitor) accountService.findAccountByID(visitorId);
            Artifact artifact = artifactService.retrieveArtifact(artifactId);
            Loan loan = mapper.readValue(body, Loan.class);
            loanee.addLoan(loan);
            artifact.setLoan(loan);
            loanService.saveLoan(loan);
            LoanDTO loanDTO = new LoanDTO(loan.getStartDate(), loan.getEndDate(), loan.getLoanStatus(), loanee.getEmail(), artifact.getName(), artifactId, artifact.getMyMuseum().getAddress());
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
/*
    @PostMapping("/approveLoan/{id}")
    public ResponseEntity createRequest() throws MuseumException {
        try {
            return new ResponseEntity<>(, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    //unecessary?
    @PostMapping("/modifyLoan/{id}")
    public ResponseEntity createRequest() throws MuseumException {
        try {
            return new ResponseEntity<>(, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/returnLoan/{id}")
    public ResponseEntity createRequest() throws MuseumException {
        try {
            return new ResponseEntity<>(, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
 */
}
