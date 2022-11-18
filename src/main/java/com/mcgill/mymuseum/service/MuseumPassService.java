package com.mcgill.mymuseum.service;
import com.mcgill.mymuseum.dto.MuseumPassDTO;
import com.mcgill.mymuseum.model.Account;
import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.model.Visitor;
import com.mcgill.mymuseum.repository.MuseumPassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

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

    public Optional<MuseumPass> retrieveMuseumPass(long id){
        return museumPassRepository.findById(id);
    }

    public MuseumPass createPass(MuseumPass museumPass, int visitorId) throws NullPointerException {
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