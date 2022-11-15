package com.mcgill.mymuseum.controller;

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

import java.sql.Date;
import java.util.List;

@RestController
public class LoanController {
    @Autowired
    LoanService loanService;
    @GetMapping("/loan/{id}")
    public ResponseEntity getLoan(@PathVariable long id) throws MuseumException {
        Date startDate = loanService.retrieveLoanById(id).getStartDate();
        Date endDate = loanService.retrieveLoanById(id).getEndDate();
        Loan.LoanStatus loanStatus = loanService.retrieveLoanById(id).getLoanStatus();
        String loaneeEmail = loanService.retrieveLoanById(id).getLoanee().getEmail();
        String artifactName = loanService.retrieveLoanById(id).getArtifact().getName();
        Long artifactId = loanService.retrieveLoanById(id).getArtifact().getArtifactId();
        String museum = loanService.retrieveLoanById(id).getMyMuseum().getAddress();
        LoanDTO loanDTO = new LoanDTO(startDate,endDate,loanStatus,loaneeEmail,artifactName,artifactId,museum);
        return new ResponseEntity<>(loanDTO, HttpStatus.OK);
    }
}
