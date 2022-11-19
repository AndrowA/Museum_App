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

import javax.transaction.Transactional;
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

    @Transactional
    @GetMapping("/loan/{id}")
    public ResponseEntity getLoan(@PathVariable long id) throws MuseumException {
        try {
            Loan loan = loanService.retrieveLoanById(id);
            LoanDTO loanDTO = new LoanDTO(loan.getStartDate(),loan.getEndDate(),loan.getLoanStatus(),loan.getLoanee().getEmail(),loan.getArtifact().getName(),loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/createLoanRequest/{visitorId}/{artifactId}")
    public ResponseEntity createRequest(@RequestBody String body,  @PathVariable(name="visitorId") Long visitorId, @PathVariable(name="artifactId") Long artifactId) throws MuseumException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Loan loan1 = mapper.readValue(body, Loan.class);
            Visitor loanee = (Visitor) accountService.findAccountByID(visitorId);
            Artifact artifact = artifactService.retrieveArtifact(artifactId);
            if (!loan1.setLoanee(loanee)){
                throw new MuseumException("Loanee already has too many loans");
            }
            //loanee.addLoan(loan1);
            if (!loan1.setArtifact(artifact)) {
                throw new MuseumException("Artifact already has a loan");
            }
            //artifact.setLoan(loan1);
            loanService.saveLoan(loan1);
            LoanDTO loanDTO = new LoanDTO(loan1.getStartDate(), loan1.getEndDate(), loan1.getLoanStatus(), loan1.getLoanee().getEmail(), loan1.getArtifact().getName(), loan1.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @PostMapping("/approveLoan/{id}")
    public ResponseEntity approveLoan(@PathVariable(name="id") Long id) throws MuseumException {
        try {
            Loan loan = loanService.acceptLoan(id);
            LoanDTO loanDTO = new LoanDTO(loan.getStartDate(), loan.getEndDate(), loan.getLoanStatus(), loan.getLoanee().getEmail(), loan.getArtifact().getName(), loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

      @PostMapping("/returnLoan/{id}")
    public ResponseEntity returnLoan(@PathVariable(name="id") Long id) throws MuseumException {
        try {
            Loan loan = loanService.returnLoan(id);
            LoanDTO loanDTO = new LoanDTO(loan.getStartDate(), loan.getEndDate(), loan.getLoanStatus(), loan.getLoanee().getEmail(), loan.getArtifact().getName(), loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rejectLoan/{id}")
    public ResponseEntity rejectLoan(@PathVariable(name="id") Long id) throws MuseumException {
        try {
            Loan loan = loanService.rejectLoan(id);
            LoanDTO loanDTO = new LoanDTO(loan.getStartDate(), loan.getEndDate(), loan.getLoanStatus(), loan.getLoanee().getEmail(), loan.getArtifact().getName(), loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
