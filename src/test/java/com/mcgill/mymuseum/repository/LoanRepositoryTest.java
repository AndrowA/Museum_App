package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Employee;
import com.mcgill.mymuseum.model.Loan;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoanRepositoryTest {

    @Autowired
    LoanRepository loanRepository;

    @AfterEach
    public void clearDatabase() {
        loanRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadLoan() {

        //create default attributes
        Date startDate = Date.valueOf("2015-03-31");
        Date endDate = Date.valueOf("2019-03-31");
        Loan.LoanStatus status = Loan.LoanStatus.Approved;

        //Create Object and set attributes
        Loan loan1 = new Loan();
        loan1.setLoanStatus(status);
        loan1.setStartDate(startDate);
        loan1.setEndDate(endDate);

        //Save Object
        loanRepository.save(loan1);

        //Read Object
        long id = loan1.getLoanId();
        loan1 = (Loan) loanRepository.findById(id).get();

        //Assert that object has correct attributes
        assertNotNull(loan1);
        assertEquals(startDate,loan1.getStartDate());
        assertEquals(endDate,loan1.getEndDate());
        assertEquals(endDate,loan1.getEndDate());
        assertEquals(status,loan1.getLoanStatus());
    }
}