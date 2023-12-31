package com.mcgill.mymuseum.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.dto.ArtifactDTO;
import com.mcgill.mymuseum.dto.LoanDTO;
import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.ArtifactService;
import com.mcgill.mymuseum.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "http://localhost:3000")
public class LoanController {
    @Autowired
    LoanService loanService;
    @Autowired
    AccountService accountService;
    @Autowired
    ArtifactService artifactService;

    /**
     * Get for all loans
     * @return dtos of loans
     * @throws MuseumException
     */
    @Transactional
    @GetMapping("/getLoans")
    public ResponseEntity getLoans() throws MuseumException {
        try{
            ArrayList<Loan> loanArrayList = (ArrayList<Loan>) loanService.getLoans();
            ArrayList<LoanDTO> dtos = new ArrayList<>();
            for(Loan loan : loanArrayList ) {
                dtos.add(new LoanDTO(loan.getLoanId(), loan.getStartDate(),loan.getEndDate(),loan.getLoanStatus(),loan.getLoanee().getEmail(),loan.getArtifact().getName(),loan.getArtifact().getArtifactId(),null));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Could not get loans.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get for a loan by id
     * @param id of loan
     * @return ResponseEntity of DTO and HTTP status
     * @throws MuseumException if fails to get loan
     */
    @Transactional
    @GetMapping("/get/{id}")
    public ResponseEntity getLoan(@PathVariable long id) throws MuseumException {
        try {
            Loan loan = loanService.retrieveLoanById(id);
            LoanDTO loanDTO = new LoanDTO(loan.getLoanId(), loan.getStartDate(),loan.getEndDate(),loan.getLoanStatus(),loan.getLoanee().getEmail(),loan.getArtifact().getName(),loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Could not get loan.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get for all loans for a loanee
     * @param id of loanee
     * @return dto of loans
     * @throws MuseumException
     */
    @Transactional
    @GetMapping("/getLoaneeLoans/{id}")
    public ResponseEntity getLoaneeLoans(@PathVariable long id) throws MuseumException {
        try {
            ArrayList<Loan> loanArrayList = (ArrayList<Loan>) loanService.retrieveLoanByVisitorId(id);
            ArrayList<LoanDTO> dtos = new ArrayList<>();
            for(Loan loan : loanArrayList ) {
                dtos.add(new LoanDTO(loan.getLoanId(), loan.getStartDate(),loan.getEndDate(),loan.getLoanStatus(),loan.getLoanee().getEmail(),loan.getArtifact().getName(),loan.getArtifact().getArtifactId(),null));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Could not get loanee's loans.",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Post method for creating a loan request
     * @param body of the post request
     * @param visitorId loaneeId that is requesting a loan
     * @param artifactId id of artifact that is being requested
     * @return ResponseEntity of DTO and HTTP status
     * @throws MuseumException if fails to create request
     */
    @Transactional
    @PostMapping("/createLoanRequest/{visitorId}/{artifactId}") //checked
    public ResponseEntity createRequest(@RequestBody String body,  @PathVariable(name="visitorId") Long visitorId, @PathVariable(name="artifactId") Long artifactId) throws MuseumException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Loan loan = mapper.readValue(body, Loan.class);
            loan.setLoanStatus(Loan.LoanStatus.InReview);
            if (!accountService.authenticate(visitorId, AccountService.TargetType.LOAN, AccountService.Action.REQUEST)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            //call the service
            loan = loanService.createLoan(loan, visitorId, artifactId);
            LoanDTO loanDTO = new LoanDTO(loan.getLoanId(), loan.getStartDate(), loan.getEndDate(), loan.getLoanStatus(), loan.getLoanee().getEmail(), loan.getArtifact().getName(), loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    /**
     * Post method to approve a loan if you are allowed to do so.
     * @param loanId if of the loan
     * @param userId id of the user approving the loan
     * @return ResponseEntity of DTO and HTTP status
     * @throws MuseumException if fails.
     */
    @Transactional
    @PostMapping("/approveLoan/{loanId}/{userId}") // checked
    public ResponseEntity approveLoan(@PathVariable(name="loanId") Long loanId, @PathVariable(name="userId") Long userId) throws MuseumException {
        try {
            if (!accountService.authenticate(userId,loanId, AccountService.Action.APPROVE)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Loan loan = loanService.acceptLoan(loanId);
            LoanDTO loanDTO = new LoanDTO(loan.getLoanId(), loan.getStartDate(), loan.getEndDate(), loan.getLoanStatus(), loan.getLoanee().getEmail(), loan.getArtifact().getName(), loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Post method for returning a loan request
     * @param loanId of loan
     * @return ResponseEntity of DTO and HTTP status
     * @throws MuseumException if fails to return a loan
     */
      @PostMapping("/returnLoan/{loanId}") // checked
    public ResponseEntity returnLoan(@PathVariable(name="loanId") Long loanId) throws MuseumException {
        try {
            Loan loan = loanService.returnLoan(loanId);
            LoanDTO loanDTO = new LoanDTO(loan.getLoanId(), loan.getStartDate(), loan.getEndDate(), Loan.LoanStatus.Available, loan.getLoanee().getEmail(), loan.getArtifact().getName(), loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Post method for rejecting a loan if allowed to do so.
     * @param loanId id of loan
     * @param userId id of user
     * @return ResponseEntity of the DTO and Http Status
     * @throws MuseumException if fails.
     */
    @PostMapping("/rejectLoan/{loanId}/{userId}")
    public ResponseEntity rejectLoan(@PathVariable(name="loanId") Long loanId,@PathVariable(name="userId") Long userId) throws MuseumException {
        try {
            if (!accountService.authenticate(userId,loanId, AccountService.Action.REMOVE)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            Loan loan = loanService.rejectLoan(loanId);
            LoanDTO loanDTO = new LoanDTO(loan.getLoanId(), loan.getStartDate(), loan.getEndDate(), Loan.LoanStatus.Rejected, loan.getLoanee().getEmail(), loan.getArtifact().getName(), loan.getArtifact().getArtifactId(),null);
            return new ResponseEntity<>(loanDTO, HttpStatus.OK);
        } catch (MuseumException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
