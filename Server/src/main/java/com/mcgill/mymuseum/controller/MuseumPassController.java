package com.mcgill.mymuseum.controller;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.dto.LoanDTO;
import com.mcgill.mymuseum.dto.MuseumPassDTO;
import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.service.AccountService;
import com.mcgill.mymuseum.service.MuseumPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@JsonFormat(pattern="yyyy-MM-dd")
@RequestMapping("/museumPass/{id}")
@CrossOrigin(origins = "http://localhost:3000")
public class MuseumPassController {
    @Autowired
    MuseumPassService museumPassService;
    @Autowired
    AccountService accountService;
    /**
     * Method to buy a museum pass
     * @param passDate
     * @param id of visitor
     * @return ResponseEntity of DTO if successful or HTTP status
     */
    @RequestMapping(method = RequestMethod.POST, path = "/buy")
    public ResponseEntity buyMuseumPass(@RequestBody String passDate, @PathVariable String id) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        try {
            MuseumPass pass = mapper.readValue(passDate, MuseumPass.class); //map date to pass
            int visitorID = Integer.parseInt(id);
            if(!accountService.authenticate(Long.parseLong(id), AccountService.TargetType.MUSEUMPASS, AccountService.Action.BUY)){ //make sure only visitor can buy
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            MuseumPass museumPassObject = museumPassService.createPass(pass, visitorID);
            MuseumPassDTO museumPassDTO = new MuseumPassDTO(museumPassObject.getPassId(), 10, museumPassObject.getPassDate(), museumPassObject.getOwner(), museumPassObject.getMyMuseum());
            return new ResponseEntity<>(museumPassDTO, HttpStatus.OK); //Create pass through DTO by associating to visitorID and return ResponseEntity OK if no errors
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Controller method to get information regarding a pass
     * @param id of pass
     * @return ResponseEntity of DTO if successful or HTTP status
     */
    @RequestMapping(method = RequestMethod.GET, path = "/info")
    public ResponseEntity getMuseumPass(@PathVariable String id) {
        try {
           MuseumPass pass2 = museumPassService.retrieveMuseumPass(Long.parseLong(id)); //retrieve MuseumPass from visitor ID
           MuseumPassDTO museumPassDTO = new MuseumPassDTO(pass2.getPassId(), 10, pass2.getPassDate(), pass2.getOwner(), pass2.getMyMuseum());
           if (pass2 == null) {
               throw new NullPointerException();
           }
           else {
               return new ResponseEntity<>(museumPassDTO, HttpStatus.OK); //return retrieved pass
           }
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getAllPasses")
    public ResponseEntity getAllMuseumPasses(@PathVariable String id) {
        try{
            ArrayList<MuseumPass> museumPassList = (ArrayList<MuseumPass>) museumPassService.getAllMuseumPasses();
            ArrayList<MuseumPassDTO> dtos = new ArrayList<>();
            for(MuseumPass museumPass : museumPassList ) {
                MuseumPassDTO newDto = new MuseumPassDTO();
                newDto.setVisitorEmail(museumPass.getOwner().getEmail());
                newDto.setVisitorEmail(museumPass.getOwner().getEmail());
                newDto.setPassId(museumPass.getPassId());
                newDto.setaPassDate(museumPass.getPassDate());
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}