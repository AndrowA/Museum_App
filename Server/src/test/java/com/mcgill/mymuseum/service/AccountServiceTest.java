package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.*;
import com.mcgill.mymuseum.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DisplayName("Account Service Test")
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    LoanRepository loanRepository;

    @Mock
    MuseumPassRepository museumPassRepository;

    @Mock
    RoomRepository roomRepository;

    @Mock
    WorkDayRepository workDayRepository;

    @AfterEach
    public void clearDatabase(){
        loanRepository.deleteAll();
        accountRepository.deleteAll();

    }

    @Test
    public void testFindTargetType(){



        // setup president
        Long presidentId = 1l;
        Mockito.when(accountRepository.findById(presidentId)).thenReturn(Optional.of(new President()));

        // setup employee
        Long employeeId = 2l;
        Mockito.when(accountRepository.findById(employeeId)).thenReturn(Optional.of(new Employee()));

        // setup visitor
        Long visitorId = 3l;
        Mockito.when(accountRepository.findById(visitorId)).thenReturn(Optional.of(new Visitor()));

        // setup artifact
        Long artifactId = 4l;
        Mockito.when(artifactRepository.findById(artifactId)).thenReturn(Optional.of(new Artifact()));

        // setup loan
        Long loanId = 5l;
        Mockito.when(loanRepository.findById(loanId)).thenReturn(Optional.of(new Loan()));

        // setup museum pass
        Long museumPassId = 6l;
        Mockito.when(museumPassRepository.findById(museumPassId)).thenReturn(Optional.of(new MuseumPass()));

        // setup room
        Long roomID = 7l;
        Mockito.when(roomRepository.findById(roomID)).thenReturn(Optional.of(new StorageRoom()));

        // setup workday
        Long workdayId = 8l;
        Mockito.when(workDayRepository.findById(workdayId)).thenReturn(Optional.of(new WorkDay()));

        assertEquals(accountService.findTargetType(presidentId), AccountService.TargetType.PRESIDENT);
        assertEquals(accountService.findTargetType(employeeId), AccountService.TargetType.EMPLOYEE);
        assertEquals(accountService.findTargetType(visitorId), AccountService.TargetType.VISITOR);
        assertEquals(accountService.findTargetType(artifactId), AccountService.TargetType.ARTIFACT);
        assertEquals(accountService.findTargetType(loanId), AccountService.TargetType.LOAN);
        assertEquals(accountService.findTargetType(museumPassId),AccountService.TargetType.MUSEUMPASS);
        assertEquals(accountService.findTargetType(roomID), AccountService.TargetType.ROOM);
        assertEquals(accountService.findTargetType(workdayId), AccountService.TargetType.WORKDAY);

    }

    @Test
    public void testAuthenticate(){

        // setup
        // setup president
        Long presidentId = 1l;
        Mockito.when(accountRepository.findById(presidentId)).thenReturn(Optional.of(new President()));

        // setup employee
        Long employeeId = 2l;
        Mockito.when(accountRepository.findById(employeeId)).thenReturn(Optional.of(new Employee()));

        // setup visitor
        Long visitorId = 3l;
        Mockito.when(accountRepository.findById(visitorId)).thenReturn(Optional.of(new Visitor()));
        // setup loan
        Long loanId = 5l;
        Mockito.when(loanRepository.findById(loanId)).thenReturn(Optional.of(new Loan()));
        // valid permissions with target ID
        assertTrue(accountService.authenticate(presidentId, visitorId, AccountService.Action.REMOVE));
        assertTrue(accountService.authenticate(visitorId, visitorId, AccountService.Action.INFO));

        // invalid permissions with target ID
        assertFalse(accountService.authenticate(employeeId,loanId, AccountService.Action.REQUEST));
        assertFalse(accountService.authenticate(visitorId, visitorId, AccountService.Action.REMOVE));

        // valid permissions with target Type
        assertTrue(accountService.authenticate(presidentId, AccountService.TargetType.LOAN, AccountService.Action.REQUEST));
        assertTrue(accountService.authenticate(visitorId, AccountService.TargetType.LOAN, AccountService.Action.REQUEST));

        // invalid permissions with target Type
        assertFalse(accountService.authenticate(employeeId, AccountService.TargetType.LOAN, AccountService.Action.REQUEST));
        assertFalse(accountService.authenticate(visitorId, AccountService.TargetType.LOAN, AccountService.Action.APPROVE));


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
        Long presidentId = 1l;
        President president = new President();
        president.setAccountId(1l);
        Mockito.when(accountRepository.findById(presidentId)).thenReturn(Optional.of(president));

        // setup employee
        Long employeeId = 2l;
        Employee employee = new Employee();
        employee.setAccountId(2l);
        Mockito.when(accountRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        // setup visitor
        Long visitorId = 3l;
        Visitor visitor = new Visitor();
        visitor.setAccountId(3l);
        Mockito.when(accountRepository.findById(visitorId)).thenReturn(Optional.of(visitor));

        assertEquals(presidentId, accountService.findAccountByID(presidentId).getAccountId());
        assertEquals(visitorId, accountService.findAccountByID(visitorId).getAccountId());
        assertEquals(employeeId, accountService.findAccountByID(employeeId).getAccountId());


    }

    @Test
    public void testFindAccountIDByEmailAndPassword(){
        String email = "test@example.com";
        String password = "password";
        Employee employee = new Employee();
        employee.setAccountId(1l);
        employee.setEmail(email);
        employee.setPassword(password);

        List<Account> accountList = new ArrayList<Account>();
        accountList.add(employee);

        Mockito.when(accountRepository.findAll()).thenReturn(accountList);

        Long id = accountService.findAccountIDByEmailAndPassword(email, password);

        assertEquals(id, 1l);
    }

    @Test
    public void testCreateAccount(){

        String email = "test@example.com";
        String password = "password";
        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setPassword(password);

        Mockito.when(accountRepository.save(Mockito.any(Employee.class))).thenReturn(employee);

        AccountService.AccountType type = AccountService.AccountType.EMPLOYEE;
        Long id = accountService.createAccount(email,password,type);

        Mockito.when(accountRepository.findById(id)).thenReturn(Optional.of(employee));
        assertEquals(accountService.findAccountByID(id).getEmail(),email);
        assertEquals(accountService.findAccountByID(id).getPassword(),password);
        assertSame(Employee.class,accountService.findAccountByID(id).getClass());

    }

    @Test
    public void testLoginAccount(){

        String email = "test@example.com";
        String password = "password";
        Employee employee = new Employee();
        employee.setEmail(email);
        employee.setPassword(password);
        Long id = 4l;
        employee.setAccountId(id);
        Employee[] employeeList = new Employee[1];
        employeeList[0] = employee;
        Mockito.when(accountRepository.findAll()).thenReturn(List.of(employeeList));
        assertEquals(accountService.loginAccount(email,password),id);

    }

    @Test
    public void testSetAccountName(){
        Long employeeId = 1l;
        String firstName = "first";
        String lastName = "Last";
        String email = "test@example.com";
        String password = "password";
        Employee employee = new Employee();
        employee.setAccountId(employeeId);
        employee.setEmail(email);
        employee.setPassword(password);

        Mockito.when(accountRepository.save(Mockito.any(Employee.class))).thenReturn(employee);
        Mockito.when(accountRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        accountService.setAccountName(employeeId, firstName,lastName);
        assertEquals(firstName, accountService.findAccountByID(employeeId).getFirstName());
        assertEquals(lastName, accountService.findAccountByID(employeeId).getLastName());
    }

    @Test
    public void testRemoveAccount(){
        Long employeeId = 1l;
        String email = "test@example.com";
        String password = "password";
        Employee employee = new Employee();
        employee.setAccountId(employeeId);
        employee.setEmail(email);
        employee.setPassword(password);
        accountRepository.save(employee);
        accountService.removeAccount(employeeId);
        assertNull(accountService.findAccountByID(employeeId));
    }
}
