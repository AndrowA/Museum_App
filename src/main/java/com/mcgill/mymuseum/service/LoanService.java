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

import java.lang.reflect.AnnotatedType;
import java.sql.Date;
import java.util.Optional;

@Service
public class LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ArtifactRepository artifactRepository;
    @Autowired
    MyMuseumRepository museumRepository;

    public Iterable<Loan> getLoans() { return loanRepository.findAll(); }

    public Loan retrieveLoanById(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()){
            return loanRepository.findById(id).get();
        } else {
            throw new MuseumException("Could not find the loan");
        }
    }

    public Loan saveLoan(Loan loan){ return loanRepository.save(loan);}

    public Loan acceptLoan(long id) throws MuseumException {
        if (loanRepository.findById(id).isPresent()) {
            if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Approved)) {
                throw new MuseumException("Loan with id: "+id+" has already been approved.");
            } else if (loanRepository.findById(id).get().getLoanStatus().equals(Loan.LoanStatus.Rejected)) {
                throw new MuseumException("Loan with id: "+id+" has already been rejected.");
            } else {
                //set status to accepted the loan
                loanRepository.findById(id).get().setLoanStatus(Loan.LoanStatus.Approved);
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loanRepository.findById(id).get();
    }
}
