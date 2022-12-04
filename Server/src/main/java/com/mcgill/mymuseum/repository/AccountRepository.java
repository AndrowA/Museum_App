package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface AccountRepository extends CrudRepository<Account,Long> {
    Optional<Account> findAccountByEmail(String email);
}
