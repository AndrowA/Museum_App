package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public Iterable<Loan> getLoans() { return loanRepository.findAll(); }

    /**
     * Service to retrieve loan by id
     * @param id of loan
     * @return Loan
     * @throws MuseumException if unable to retrieve loan
     */
    @Transactional
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
    @Transactional
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
        //validate that the visitor doesn't already have 3 loans
        if (loanee.getLoans().size() == Visitor.maximumNumberOfLoans()) {
            throw new MuseumException("Visitor has reached the maximum amount of loans already.");
        }
        loan.setLoanee(loanee);
        //validate that the artifact is available
        if (artifact.hasLoan()) {
            if (!artifact.getLoan().getLoanStatus().equals(Loan.LoanStatus.Available)){
                throw new MuseumException("Artifact not available for loan.");
            }
            artifact.setLoan(null);
        }
        loan.setArtifact(artifact);
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
        Optional<Loan> loan1 = loanRepository.findById(id);
        if (loan1.isPresent()) {
            Loan loan = loan1.get();
            if (loan.getLoanStatus().equals(Loan.LoanStatus.Approved)) {
                throw new MuseumException("Loan with id: " + id + " has already been approved.");
            } else if (loan.getLoanStatus().equals(Loan.LoanStatus.Rejected)) {
                throw new MuseumException("Loan with id: " + id + " has already been rejected.");
            } else if (loan.getLoanStatus().equals(Loan.LoanStatus.Available)) {
                throw new MuseumException("Loan with id: "+id+" has already been returned and is available.");
            } else {
                loanRepository.deleteById(loan.getLoanId());
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loan1.get();
    }

    /**
     * Service to return a loan to the museum.
     * @param id of loan
     * @return Loan
     * @throws MuseumException
     */
    @Transactional
    public Loan returnLoan(long id) throws MuseumException {
        Optional<Loan> loan1 = loanRepository.findById(id);
        if (loan1.isPresent()) {
            Loan loan = loan1.get();
            if (loan.getLoanStatus().equals(Loan.LoanStatus.InReview)) {
                throw new MuseumException("Loan with id: " + id + " is still in review.");
            } else if (loan.getLoanStatus().equals(Loan.LoanStatus.Rejected)) {
                throw new MuseumException("Loan with id: " + id + " has already been rejected.");
            } else if (loan.getLoanStatus().equals(Loan.LoanStatus.Available)) {
                throw new MuseumException("Loan with id: "+id+" has already been returned and is available.");
            } else {
                loanRepository.deleteById(loan.getLoanId());
            }
        } else {
            throw new MuseumException("Loan with id: "+id+" does not exist.");
        }
        return loan1.get();
    }
}
