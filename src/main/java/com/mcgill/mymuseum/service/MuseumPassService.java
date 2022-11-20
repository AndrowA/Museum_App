package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MuseumPassService {
    @Autowired
     VisitorService visitorService;
    @Autowired
     MuseumPassRepository museumPassRepository;

    /**
     * Method to get all museum passes
     * @return list of museum passes
     */
    public Iterable<MuseumPass> getAllMuseumPasses(){
        return museumPassRepository.findAll();
    }

    /**
     * Method to retrieve museum pass by id
     * @param id of pass
     * @return museum pass
     */
    public MuseumPass retrieveMuseumPass(Long id){
        return museumPassRepository.findById(id).isPresent()?museumPassRepository.findById(id).get():null;

    }

    /**
     * Method to create pass for a visitor
     * @param museumPass
     * @param visitorId
     * @return museumPass
     * @throws NullPointerException
     */
    public MuseumPass createPass(MuseumPass museumPass, int visitorId) throws NullPointerException {
        Visitor visitor = visitorService.retrieveVisitor(visitorId);
        museumPass.setMyMuseum(visitor.getMyMuseum());
        museumPass.setPassId(museumPass.getPassId());
        museumPass.setPassDate(museumPass.getPassDate());
        museumPass.setOwner(visitor);
        museumPass.setPassCost(10);

        if (visitor == null) {
            throw new NullPointerException("Could not retrieve visitor with id " + visitorId);
        } else {
            visitor.addPass(museumPass);
            return museumPassRepository.save(museumPass);

        }
    }
}