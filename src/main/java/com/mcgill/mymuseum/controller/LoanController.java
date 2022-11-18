package com.mcgill.mymuseum.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mcgill.mymuseum.dto.LoanDTO;
import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class LoanController {
    @Autowired
    LoanService loanService;
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
}
