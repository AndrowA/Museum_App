package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.Account;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VisitorService {

    AccountRepository visitorRepository;

    public VisitorService(@Autowired AccountRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }


    public Visitor createVisitor(Visitor visitor) throws NullPointerException {
        try {
            Visitor createdVisitor = visitorRepository.save(visitor);
            return createdVisitor;
        } catch (NullPointerException e) {
            throw new NullPointerException("Visitor already existed");
        }
    }
    public Visitor retrieveVisitor(long id) {
        try {
            Optional<Account> optionalAccount = visitorRepository.findById(id);
            Account a = optionalAccount.get();
            if(a instanceof Visitor) {
                return (Visitor) a;
            }
            else {
                throw new NullPointerException("visitor with id "+ id + " does not exist");
            }
        }
        catch(NoSuchElementException ex) {
            return null;
        }
    }


}