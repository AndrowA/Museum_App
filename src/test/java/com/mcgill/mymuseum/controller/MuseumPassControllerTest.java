package com.mcgill.mymuseum.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mcgill.mymuseum.dto.MuseumPassDTO;
import com.mcgill.mymuseum.model.MyMuseum;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.AccountRepository;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import com.mcgill.mymuseum.service.MuseumPassService;
import com.mcgill.mymuseum.service.VisitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("integration")
@SpringBootTest
public class MuseumPassControllerTest {
    @Autowired
     MuseumPassController passController;
    @Autowired
     MuseumPassRepository passRepository;
    @Autowired
     VisitorService visitorService;
    @Autowired
     MuseumPassService passService;
    @Autowired
     AccountRepository accountRepository;
    @JsonFormat(pattern="yyyy-MM-dd")

    @BeforeEach
    public void clearDatabase(){
        passRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateMuseumPassValid() throws ParseException {
        // account DTO
        MyMuseum museum = new MyMuseum();
        String id = "777";
        String passDate = "2022-11-07";
        MuseumPassDTO museumPassDTO = new MuseumPassDTO();
        Visitor visitor = new Visitor();
        visitor.setPassword("1111");
        visitor.setEmail("h@gmail.com");
        visitor.setAccountId(Long.parseLong(id));
        visitor.setMyMuseum(museum);
        ResponseEntity<Long> out =  passController.buyMuseumPass(passDate,id);
        assertTrue(out.getStatusCode().equals(HttpStatus.OK));
        assertEquals(passDate, passService.retrieveMuseumPass(out.getBody()).getPassDate());
        assertEquals(id, passService.retrieveMuseumPass(out.getBody()).getPassId());
    }

    @Test
    public void testCreateMuseumPassInValid() throws ParseException {
        // account DTO
        MyMuseum museum = new MyMuseum();
        String id = "777";
        String passDate = "2022-11-07";
        MuseumPassDTO museumPassDTO = new MuseumPassDTO();
        Visitor visitor = new Visitor();
        visitor.setPassword("1111");
        visitor.setEmail("h@gmail.com");
        visitor.setAccountId(Long.parseLong(id));
        visitor.setMyMuseum(museum);
        ResponseEntity<Long> out =  passController.buyMuseumPass(passDate,id);
        assertTrue(out.getStatusCode().equals(HttpStatus.OK));
        assertEquals(passDate, passService.retrieveMuseumPass(out.getBody()).getPassDate());
        assertEquals(id, passService.retrieveMuseumPass(out.getBody()).getPassId());
    }
}
