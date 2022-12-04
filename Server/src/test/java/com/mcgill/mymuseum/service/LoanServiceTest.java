package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.repository.ArtifactRepository;
import com.mcgill.mymuseum.repository.LoanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    LoanRepository loanRepository;
    @Mock
    AccountRepository accountRepository;
    @Mock
    ArtifactRepository artifactRepository;
    @InjectMocks
    LoanService loanService;

    @Nested
    @DisplayName("Test get loan method")
    class GetLoan {
        @Test
        void getLoan() throws MuseumException {
            long id = 1;
            Loan loan = new Loan();
            loan.setLoanId(id);
            Mockito.when(loanRepository.findById(id)).thenReturn(Optional.of(loan));
            try {
                assertEquals(loanService.retrieveLoanById(id),loan);
            } catch (MuseumException e) {
                throw new MuseumException(e.getMessage());
            }
        }

        @Test
        @DisplayName("get all loans")
        void getAllLoans() {
            List<Loan> listOfLoans = new ArrayList<Loan>();
            Mockito.when(loanRepository.findAll()).thenReturn(listOfLoans);
            assertEquals(listOfLoans, loanService.getLoans());
            Mockito.verify(loanRepository).findAll();
        }

        @Test
        @DisplayName("get loan by visitor loan successfully")
        void getLoanByVisitor() throws MuseumException {
            List<Loan> listOfLoans = new ArrayList<>();
            Visitor visitor = new Visitor();
            visitor.setEmail("robert@gmail.com");
            long visitorID = 1;
            visitor.setAccountId(visitorID);
            Loan loan = new Loan();
            loan.setLoanee(visitor);
            listOfLoans.add(loan);
            Mockito.when(loanRepository.findByLoaneeAccountId(visitor.getAccountId())).thenReturn(listOfLoans);
            try {
                assertEquals(loanService.retrieveLoanByVisitorId(visitor.getAccountId()),listOfLoans);
            } catch (MuseumException e) {
                throw new MuseumException(e.getMessage());
            }
        }

        @Test
        @DisplayName("saving loan successfully")
        void saveLoan() {
            Loan loan = new Loan();
            Mockito.when(loanRepository.save(loan)).thenReturn(loan);
            try {
                assertEquals(loan, loanService.saveLoan(loan));
                Mockito.verify(loanRepository).save(loan);
            }catch(Exception e){
                fail();
            }
        }

        @Test
        @DisplayName("Approve loan successfully")
        void acceptLoan() throws MuseumException {
            long id = 1;
            Loan loan = new Loan();
            loan.setLoanId(id);
            loan.setLoanStatus(Loan.LoanStatus.InReview);
            Mockito.when(loanRepository.findById(id)).thenReturn(Optional.of(loan));
            try {
                assertEquals(loanService.acceptLoan(id),loan);
            } catch (MuseumException e) {
                throw new MuseumException(e.getMessage());
            }
        }

        @Test
        @DisplayName("Reject loan successfully")
        void rejectLoan() throws MuseumException {
            long id = 1;
            Loan loan = new Loan();
            loan.setLoanId(id);
            loan.setLoanStatus(Loan.LoanStatus.InReview);
            Mockito.when(loanRepository.findById(id)).thenReturn(Optional.of(loan));
            try {
                assertEquals(loanService.rejectLoan(id),loan);
            } catch (MuseumException e) {
                throw new MuseumException(e.getMessage());
            }
        }

        @Test
        @DisplayName("Return loan successfully")
        void returnLoan() throws MuseumException {
            long id = 1;
            Loan loan = new Loan();
            loan.setLoanId(id);
            loan.setLoanStatus(Loan.LoanStatus.Approved);
            Mockito.when(loanRepository.findById(id)).thenReturn(Optional.of(loan));
            try {
                assertEquals(loanService.returnLoan(id),loan);
            } catch (MuseumException e) {
                throw new MuseumException(e.getMessage());
            }
        }
    }
}
