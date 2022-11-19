package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.dto.ArtifactDTO;
import com.mcgill.mymuseum.dto.MyMuseumDTO;
import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.ArtifactRepository;
import com.mcgill.mymuseum.repository.LoanRepository;
import com.mcgill.mymuseum.repository.MyMuseumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.AnnotatedType;
import java.sql.Date;
import java.util.Optional;

/**
 * @author Radu Petrescu
 */
@Service
public class LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    ArtifactService artifactService;

    /**
     * Service for getting all loans
     * @return Iterable<Loan>
     */
    public Iterable<Loan> getLoans() { return loanRepository.findAll(); }

    /**
     * Service to retrieve loan by id
     * @param id of loan
     * @return Loan
     * @throws MuseumException if unable to retrieve loan
     */
    public Loan retrieveLoanById(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()){
            return loanRepository.findById(id).get();
        } else {
            throw new MuseumException("Could not find the loan");
        }
    }

    /**
     * Service for saving a loan
     * @param loan
     * @return loan
     */
    public Loan saveLoan(Loan loan){ return loanRepository.save(loan);}

    /**
     * Service that creates a loan and adds it to its loanee and artifact.
     * @param loan
     * @param visitorId
     * @param artifactId
     * @return Loan
     * @throws Exception
     */
    @Transactional
    public Loan createLoan(Loan loan, long visitorId, long artifactId) throws Exception {
        Visitor loanee = (Visitor) accountService.findAccountByID(visitorId);
        Artifact artifact = artifactService.retrieveArtifact(artifactId);
        if (artifact == null){
            throw new MuseumException("Could not retrieve artifact with id " + artifactId);
        } else {
            artifact.setLoan(loan);
        }
        if (loanee == null) {
            throw new MuseumException("Could not retrieve visitor with id " + visitorId);
        } else {
            loanee.addLoan(loan);
        }
        loanRepository.save(loan);
        return loan;
    }

    /**
     * Service that accepts a loan
     * @param id of loan
     * @return loan
     * @throws MuseumException
     */
    @Transactional
    public Loan acceptLoan(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()) {
            Loan loan = loanRepository.findById(id).get();
            if (loan.getLoanStatus().equals(Loan.LoanStatus.Approved)) {
                throw new MuseumException("Loan with id: "+id+" has already been approved.");
            } else if (loan.getLoanStatus().equals(Loan.LoanStatus.Rejected)) {
                throw new MuseumException("Loan with id: "+id+" has already been rejected.");
            } else if (loan.getLoanStatus().equals(Loan.LoanStatus.Available)) {
                throw new MuseumException("Loan with id: " + id + " cannot be accepted since it wasn't requested yet.");
            } else {
                //set status to accepted the loan
                loan.setLoanStatus(Loan.LoanStatus.Approved);
                loanRepository.save(loan);
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loanRepository.findById(id).get();
    }

    /**
     * Service that rejects a loan
     * @param id of loan
     * @return loan
     * @throws MuseumException
     */
    @Transactional
    public Loan rejectLoan(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()) {
            if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Approved)) {
                throw new MuseumException("Loan with id: " + id + " has already been approved.");
            } else if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Rejected)) {
                throw new MuseumException("Loan with id: " + id + " has already been rejected.");
            } else if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Available)) {
                throw new MuseumException("Loan with id: "+id+" has already been returned and is available.");
            } else {
                //set status to available the loan
                loanRepository.findById(id).get().setLoanStatus(Loan.LoanStatus.Rejected);
                //remove loan from loanee
                loanRepository.findById(id).get().getLoanee().removeLoan(loanRepository.findById(id).get());
                //remove loan from artifact
                loanRepository.findById(id).get().getArtifact().setLoan(null);
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loanRepository.findById(id).get();
    }

    /**
     * Service to return a loan to the museum.
     * @param id of loan
     * @return Loan
     * @throws MuseumException
     */
    @Transactional
    public Loan returnLoan(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()) {
            Loan loan = loanRepository.findById(id).get();
            if (loan.getLoanStatus().equals(Loan.LoanStatus.InReview)) {
                throw new MuseumException("Loan with id: " + id + " is still in review.");
            } else if (loan.getLoanStatus().equals(Loan.LoanStatus.Rejected)) {
                throw new MuseumException("Loan with id: " + id + " has already been rejected.");
            } else if (loan.getLoanStatus().equals(Loan.LoanStatus.Available)) {
                throw new MuseumException("Loan with id: "+id+" has already been returned and is available.");
            } else {
                //set status to available the loan
                System.out.println("here1");
                loan.setLoanStatus(Loan.LoanStatus.Available);
                //remove loan from loanee
                System.out.println("here2");
                loan.getLoanee().removeLoan(loanRepository.findById(id).get());
                //remove loan from artifact
                if (loan.getArtifact().setLoan(null)){
                    System.out.println("here3");
                }
                loanRepository.save(loan);
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loanRepository.findById(id).get();
    }
}
