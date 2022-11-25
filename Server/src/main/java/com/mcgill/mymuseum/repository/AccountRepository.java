package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Account;
import org.springframework.data.repository.CrudRepository;


public interface AccountRepository extends CrudRepository<Account,Long> {
}
