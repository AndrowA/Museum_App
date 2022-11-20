package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.*;
import com.mcgill.mymuseum.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    AccountService accountService;
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

    @AfterEach
    public void clearDatabase(){
        loanRepository.deleteAll();
        accountRepository.deleteAll();

    }

    @Test
    public void testFindTargetType(){
        // setup president
        President president =  new President();
        president = accountRepository.save(president);
        Long presidentId = president.getAccountId();

        // setup employee
        Employee employee = new Employee();
        employee = accountRepository.save(employee);
        Long employeeId = employee.getAccountId();

        // setup visitor
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Long visitorID = visitor.getAccountId();

        // setup artifact
        Artifact artifact = new Artifact();
        artifact = artifactRepository.save(artifact);
        Long artifactId = artifact.getArtifactId();

        // setup loan
        Loan loan = new Loan();
        loan = loanRepository.save(loan);
        Long loanId = loan.getLoanId();

        // setup museum pass
        MuseumPass museumPass = new MuseumPass();
        museumPass = museumPassRepository.save(museumPass);
        Long museumPassId = museumPass.getPassId();

        // setup room
        Room room = new StorageRoom();
        room = roomRepository.save(room);
        Long roomID = room.getRoomId();

        // setup workday
        WorkDay workday = new WorkDay();
        workday = workDayRepository.save(workday);
        Long workdayID = workday.getId();

        assertEquals(accountService.findTargetType(presidentId), AccountService.TargetType.PRESIDENT);
        assertEquals(accountService.findTargetType(employeeId), AccountService.TargetType.EMPLOYEE);
        assertEquals(accountService.findTargetType(visitorID), AccountService.TargetType.VISITOR);
        assertEquals(accountService.findTargetType(artifactId), AccountService.TargetType.ARTIFACT);
        assertEquals(accountService.findTargetType(loanId), AccountService.TargetType.LOAN);
        assertEquals(accountService.findTargetType(museumPassId),AccountService.TargetType.MUSEUMPASS);
        assertEquals(accountService.findTargetType(roomID), AccountService.TargetType.ROOM);
        assertEquals(accountService.findTargetType(workdayID), AccountService.TargetType.WORKDAY);

    }

    @Test
    public void testAuthenticate(){

        // setup
        // setup president
        President president =  new President();
        president = accountRepository.save(president);
        Long presidentId = president.getAccountId();

        // setup employee
        Employee employee = new Employee();
        employee = accountRepository.save(employee);
        Long employeeId = employee.getAccountId();

        // setup visitor
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Long visitorID = visitor.getAccountId();


        // setup loan (target object)
        Loan loan = new Loan();
        //loan.setLoanee(visitor);
        loan = loanRepository.save(loan);
        loan.setLoanee(visitor);
        loan = loanRepository.save(loan);
        Long loanId = loan.getLoanId();

        // valid permissions with target ID
        assertTrue(accountService.authenticate(presidentId, loanId, AccountService.Action.CANCEL));
        assertTrue(accountService.authenticate(visitorID, loanId, AccountService.Action.CANCEL));

        // invalid permissions with target ID
        assertFalse(accountService.authenticate(employeeId,loanId, AccountService.Action.REQUEST));
        assertFalse(accountService.authenticate(visitorID,loanId, AccountService.Action.APPROVE));

        // valid permissions with target Type
        assertTrue(accountService.authenticate(presidentId, AccountService.TargetType.LOAN, AccountService.Action.REQUEST));
        assertTrue(accountService.authenticate(visitorID, AccountService.TargetType.LOAN, AccountService.Action.REQUEST));

        // invalid permissions with target Type
        assertFalse(accountService.authenticate(employeeId, AccountService.TargetType.LOAN, AccountService.Action.REQUEST));
        assertFalse(accountService.authenticate(visitorID, AccountService.TargetType.LOAN, AccountService.Action.APPROVE));


    }

    @Test
    public void testIsValidEmail(){
        String validEmail1 = "joe.biden@whitehouse.com";
        String validEmail2 = "Abc.123@example.com";
        String invalidEmail1 = "plainaddress";
        String invalidEmail2 = "#@%^%#$@#$@#.com";
        String invalidEmail3 = "Joe Smith <email@example.com>";
        String invalidEmail4 = "email@example@example.com";


        assertTrue(accountService.isValidEmail(validEmail1));
        assertTrue(accountService.isValidEmail(validEmail2));
        assertFalse(accountService.isValidEmail(invalidEmail1));
        assertFalse(accountService.isValidEmail(invalidEmail2));
        assertFalse(accountService.isValidEmail(invalidEmail3));
        assertFalse(accountService.isValidEmail(invalidEmail4));


    }

    @Test
    public void testFindAccountByID(){
        // setup president
        President president =  new President();
        president = accountRepository.save(president);
        Long presidentId = president.getAccountId();

        // setup employee
        Employee employee = new Employee();
        employee = accountRepository.save(employee);
        Long employeeId = employee.getAccountId();

        // setup visitor
        Visitor visitor = new Visitor();
        visitor = accountRepository.save(visitor);
        Long visitorID = visitor.getAccountId();
        assertEquals(president.getAccountId(), accountService.findAccountByID(presidentId).getAccountId());
        assertEquals(visitor.getAccountId(), accountService.findAccountByID(visitorID).getAccountId());
        assertEquals(employee.getAccountId(), accountService.findAccountByID(employeeId).getAccountId());
    }

    @Test
    public void testCreateAccount(){

        String email = "test@example.com";
        String password = "password";
        AccountService.AccountType type = AccountService.AccountType.EMPLOYEE;
        Long id = accountService.createAccount(email,password,type);

        Account account = accountService.findAccountByID(id);
        System.out.println("\n Printing begins \n");
        System.out.println(account.toString());


        assertEquals(accountService.findAccountByID(id).getEmail(),email);
        assertEquals(accountService.findAccountByID(id).getPassword(),password);
        assertSame(Employee.class,accountService.findAccountByID(id).getClass());

    }

    @Test
    public void testLoginAccount(){

        String email = "test@example.com";
        String password = "password";
        AccountService.AccountType type = AccountService.AccountType.EMPLOYEE;
        Long id = accountService.createAccount(email,password,type);


        assertEquals(accountService.loginAccount(email,password),id);

    }

    @Test
    public void testRemoveAccount(){
        String email = "test@example.com";
        String password = "password";
        AccountService.AccountType type = AccountService.AccountType.EMPLOYEE;
        Long id = accountService.createAccount(email,password,type);

        assertTrue(accountService.removeAccount(id));

    }

}
