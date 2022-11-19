package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.*;
import com.mcgill.mymuseum.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;


@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ArtifactRepository artifactRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    MuseumPassRepository museumPassRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    WorkDayRepository workDayRepository;


    public enum AccountType {
        VISITOR,
        EMPLOYEE,
    }

    public enum TargetType {
        VISITOR,
        EMPLOYEE,
        ARTIFACT,
        LOAN,
        MUSEUMPASS,
        ROOM,
        WORKDAY
    }

    public enum Action {
        BUY,
        INFO,
        REQUEST,
        APPROVE,
        CANCEL,
        MODIFY,
        ASSIGN
    }

    public TargetType findTargetType(long targetId){
        if (accountRepository.findById(targetId).isPresent()){
            if (accountRepository.findById(targetId).get() instanceof Employee){
                return TargetType.EMPLOYEE;
            }else if (accountRepository.findById(targetId).get() instanceof Visitor){
                return TargetType.VISITOR;
            }
            return null;
        }else if (artifactRepository.findById(targetId).isPresent()){
            return TargetType.ARTIFACT;
        } else if (loanRepository.findById(targetId).isPresent()){
            return TargetType.LOAN;
        }else if (museumPassRepository.findById(targetId).isPresent()){
            return TargetType.MUSEUMPASS;
        }else if (roomRepository.findById(targetId).isPresent()){
            return TargetType.ROOM;
        }else if (workDayRepository.findById(targetId).isPresent()){
            return TargetType.WORKDAY;
        }
        return null;
    }

    /**
     * @param accountId Account ID of the current user
     * @param targetId Object with which user would like to interact can be any class
     * @return if the current user is permitted to perform actions in target
     * function checks if the target object has restricted access depending on the type of user
     */
    public boolean authenticate(long accountId,long targetId, Action action){


        TargetType type = findTargetType(targetId);

        // list of permissions that employee has
        boolean employeePermissions = (type.equals(TargetType.LOAN) && (action.equals(Action.APPROVE)||action.equals(Action.INFO)))||
                (type.equals(TargetType.MUSEUMPASS) && action.equals(Action.INFO)) ||
                (type.equals(TargetType.ARTIFACT) && (action.equals(Action.INFO)||action.equals(Action.MODIFY)||action.equals(Action.ASSIGN)) ||
                (type.equals(TargetType.WORKDAY) && workDayRepository.findById(targetId).get().getEmployee().getAccountId()==accountId && action.equals(Action.INFO))) ||
                (targetId==accountId);

        // list of permissions that visitor has
        boolean visitorPermissions = (type.equals(TargetType.LOAN) &&((action.equals(Action.INFO)|| action.equals(Action.CANCEL) && loanRepository.findById(targetId).get().getLoanee().getAccountId()==accountId)||action.equals(Action.REQUEST))) ||
                (type.equals(TargetType.MUSEUMPASS) && (action.equals(Action.BUY) || action.equals(Action.INFO))) ||
                (type.equals(TargetType.ARTIFACT) && (action.equals(Action.INFO))) ||
                (targetId==accountId);

        if (accountRepository.findById(accountId).isPresent()){
            Account account = accountRepository.findById(accountId).get();
            if (account instanceof President){
                return true;
            } else if (account instanceof Employee){
                return employeePermissions;
            } else if(account instanceof Visitor){
                return visitorPermissions;
            }
        }

        return false;
    }

    public boolean authenticate(long accountId, TargetType type, Action action){
        boolean employeePermissions = (type.equals(TargetType.LOAN) && (action.equals(Action.APPROVE)||action.equals(Action.INFO)))||
                (type.equals(TargetType.MUSEUMPASS) && action.equals(Action.INFO)) ||
                (type.equals(TargetType.ARTIFACT) && (action.equals(Action.INFO)||action.equals(Action.MODIFY)||action.equals(Action.ASSIGN)));

        // list of permissions that visitor has
        boolean visitorPermissions = (type.equals(TargetType.LOAN) || action.equals(Action.REQUEST)) ||
                (type.equals(TargetType.MUSEUMPASS) && (action.equals(Action.BUY))) ||
                (type.equals(TargetType.ARTIFACT) && (action.equals(Action.INFO)));

        if (accountRepository.findById(accountId).isPresent()){
            Account account = accountRepository.findById(accountId).get();
            if (account instanceof President){
                return true;
            } else if (account instanceof Employee){
                return employeePermissions;
            } else if(account instanceof Visitor){
                return visitorPermissions;
            }
        }

        return false;
    }

    public static boolean isValidEmailAddrRegex(String emailAddrToValidate) {
        return Pattern.compile("^[\\p{L}0-9!#$%&'*+\\/=?^_`{|}~-][\\p{L}0-9.!#$%&'*+\\/=?^_`{|}~-]{0,63}@[\\p{L}0-9-]+(?:\\.[\\p{L}0-9-]{2,7})*$") // 1
                .matcher(emailAddrToValidate) // 2
                .matches(); // 3
    }

    public Account findAccountByID(Long id){
        return accountRepository.findById(id).isPresent()?accountRepository.findById(id).get():null;
    }
    public Long findAccountIDByEmailAndPassword(String email, String password){

        Iterable<Account> accounts = accountRepository.findAll();

        for (Account account: accounts){
            if (account.getEmail().equals(email) && account.getPassword().equals(password)){
                return Long.valueOf(account.getAccountId());
            }
        }
        return null;
    }

    public Long createAccount(String email, String password, AccountType accountType){
        if (!isValidEmailAddrRegex(email)){
            throw new Error("Invalid Email");
        }
        if (findAccountIDByEmailAndPassword(email,password)==null){
            if (accountType.equals(AccountType.VISITOR)){
                Visitor newVisitor = new Visitor();
                newVisitor.setEmail(email);
                newVisitor.setPassword(password);
                newVisitor = accountRepository.save(newVisitor);
                return newVisitor.getAccountId();
            } else if (accountType.equals(AccountType.EMPLOYEE)){
                Employee newEmployee = new Employee();
                newEmployee.setEmail(email);
                newEmployee.setPassword(password);
                newEmployee = accountRepository.save(newEmployee);
                return newEmployee.getAccountId();
            }
            throw new Error("Ambiguous account type");
        }else {
            throw new Error("Account already exists");
        }
    }

    public Long loginAccount(String email, String password){
        Long accountId = findAccountIDByEmailAndPassword(email,password);
        if (accountId!=null){
            return accountId;
        }
        throw new Error("Account not found");
    }
}

