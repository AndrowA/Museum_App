package com.mcgill.mymuseum.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MuseumPassServiceTest {
    @Mock
    private MuseumPassRepository passRepository;
    @Mock
    private AccountRepository visitorRepository;
    @InjectMocks
    MuseumPassService museumPassService;
    @JsonFormat(pattern="yyyy-MM-dd")

    @Test
    public void testGetPassById() {
        Visitor visitor = new Visitor();
        visitor = visitorRepository.save(visitor);
        Long visitorID = visitor.getAccountId();
        MuseumPass museumPass = new MuseumPass();
        MuseumPass pass = museumPassService.createPass(museumPass, Integer.parseInt(String.valueOf(visitorID)));
        assertEquals(pass.getPassId(), museumPassService.retrieveMuseumPass(visitorID).getPassId());
    }
    @Test
    public void testCreatePass(){

        MuseumPass pass = new MuseumPass();
        Integer visitorId = 1;
        MuseumPass passTest = museumPassService.createPass(pass, visitorId);
        MuseumPass passTest2 = museumPassService.retrieveMuseumPass(visitorId);
        assertEquals(passTest2, passTest);
        System.out.println("\n Printing begins \n");
        System.out.println(passTest2.toString());


        assertEquals(museumPassService.retrieveMuseumPass(visitorId).getOwner().getAccountId(),visitorId);

    }
}
//    @Test
//    public void testGetPersonByInvalidId() {
//        final int invalidId = 99;
//        when(personRepo.findPersonById(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
//
//        EventRegistrationException ex = assertThrows(EventRegistrationException.class, () -> personService.getPersonById(invalidId));
//
//        assertEquals("Person not found.", ex.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
//    }
//
//    @Test
//    public void testCreatePerson() {
//        // Just return the Person with no modification
//        when(personRepo.save(any(Person.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
//
//        final String name = "Anakin Skywalker";
//        final Person anakin = new Person(name);
//        Person returnedPerson = personService.createPerson(anakin);
//
//        assertNotNull(returnedPerson);
//        assertEquals(name, returnedPerson.getName());
//        // Check that the service actually saved the person
//        verify(personRepo, times(1)).save(anakin);
//    }
//}
//    }
//
//
//
//    {
//
//    }
//
//
//
//}
