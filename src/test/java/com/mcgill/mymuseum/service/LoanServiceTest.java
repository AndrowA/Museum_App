package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.exceptions.MuseumException;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.repository.LoanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    LoanRepository loanRepository;
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
    }
}
