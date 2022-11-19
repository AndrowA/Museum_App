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

@Service
public class LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    ArtifactService artifactService;

    public Iterable<Loan> getLoans() { return loanRepository.findAll(); }

    public Loan retrieveLoanById(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()){
            return loanRepository.findById(id).get();
        } else {
            throw new MuseumException("Could not find the loan");
        }
    }

    public Loan saveLoan(Loan loan){ return loanRepository.save(loan);}

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

    @Transactional
    public Loan acceptLoan(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()) {
            if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Approved)) {
                throw new MuseumException("Loan with id: "+id+" has already been approved.");
            } else if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Rejected)) {
                throw new MuseumException("Loan with id: "+id+" has already been rejected.");
            } else if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Available)) {
                throw new MuseumException("Loan with id: " + id + " cannot be accepted since it wasn't requested yet.");
            } else {
                //set status to accepted the loan
                loanRepository.findById(id).get().setLoanStatus(Loan.LoanStatus.Approved);
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loanRepository.findById(id).get();
    }

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
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loanRepository.findById(id).get();
    }

    @Transactional
    public Loan returnLoan(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()) {
            if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.InReview)) {
                throw new MuseumException("Loan with id: " + id + " is still in review.");
            } else if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Rejected)) {
                throw new MuseumException("Loan with id: " + id + " has already been rejected.");
            } else if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Available)) {
                throw new MuseumException("Loan with id: "+id+" has already been returned and is available.");
            } else {
                //set status to available the loan
                loanRepository.findById(id).get().setLoanStatus(Loan.LoanStatus.Available);
                //remove loan from loanee
                loanRepository.findById(id).get().getLoanee().removeLoan(loanRepository.findById(id).get());
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loanRepository.findById(id).get();
    }
}
