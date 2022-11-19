package com.mcgill.mymuseum.controller;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.dto.MuseumPassDTO;
import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.service.MuseumPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@JsonFormat(pattern="yyyy-MM-dd")
@RequestMapping("/museumPass/{id}")
public class MuseumPassController {
    @Autowired
    MuseumPassService museumPassService;

    @RequestMapping(method = RequestMethod.POST, path = "/buy")
    public ResponseEntity buyMuseumPass(@RequestBody String passDate, @PathVariable String id) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            MuseumPass pass = mapper.readValue(passDate, MuseumPass.class);
            int visitorID = Integer.parseInt(id);
            MuseumPass museumPassObject = museumPassService.createPass(pass, visitorID);
            MuseumPassDTO museumPassDTO = new MuseumPassDTO(museumPassObject.getPassId(), 10, museumPassObject.getPassDate(), museumPassObject.getOwner(), museumPassObject.getMyMuseum());
            return new ResponseEntity<>(museumPassDTO, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/info")
    public ResponseEntity getMuseumPass(@PathVariable String id) {
        try {
           Optional<MuseumPass> pass2 = museumPassService.retrieveMuseumPass(Integer.parseInt(id));
           MuseumPassDTO museumPassDTO = new MuseumPassDTO(pass2.get().getPassId(), 10, pass2.get().getPassDate(), pass2.get().getOwner(), pass2.get().getMyMuseum());
           if (pass2 == null) {
               throw new NullPointerException();
           }
           else {
               return new ResponseEntity<>(museumPassDTO, HttpStatus.OK);
           }
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }


    }
}