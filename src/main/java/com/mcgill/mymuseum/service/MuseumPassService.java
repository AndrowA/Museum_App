package com.mcgill.mymuseum.service;
import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MuseumPassService {
    private VisitorService visitorService;
    private MuseumPassRepository museumPassRepository;

    public MuseumPassService(@Autowired MuseumPassRepository museumPassRepository, @Autowired VisitorService visitorService){
        this.museumPassRepository = museumPassRepository;
        this.visitorService = visitorService;
    }

    public Iterable<MuseumPass> getAllMuseumPasses(){
        return museumPassRepository.findAll();
    }

    public MuseumPass retrieveMuseumPass(Long id){ //retrieve museum pass from visitor ID
        return museumPassRepository.findById(id).isPresent()?museumPassRepository.findById(id).get():null;

    }

    public MuseumPass createPass(MuseumPass museumPass, int visitorId) throws NullPointerException { //create pass for visitor
        Visitor visitor = visitorService.retrieveVisitor(visitorId);
        museumPass.setMyMuseum(visitor.getMyMuseum());
        museumPass.setPassId(museumPass.getPassId());
        museumPass.setPassDate(museumPass.getPassDate());
        museumPass.setOwner(visitor);
        museumPass.setPassCost(10);

        if (visitor == null) {
            throw new NullPointerException("Could not retrieve visitor with id " + visitorId);
        }
        else {
            visitor.addPass(museumPass);
            return museumPassRepository.save(museumPass);

        }

    }

}