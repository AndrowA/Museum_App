package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan,Long> {
    public Iterable<Loan> findByLoaneeAccountId(Long id);
}
