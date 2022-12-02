package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.repository.ArtifactRepository;
import com.mcgill.mymuseum.repository.LoanRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Setter
public class ArtifactService {

    @Autowired
    ArtifactRepository artifactRepository;
    @Autowired
    LoanRepository loanRepository;

    /**
     * Method that returns all artifacts
     * @return List of artifacts
     */
    @Transactional
    public Iterable<Artifact> getAllArtifacts(){
        return artifactRepository.findAll();
    }

    /**
     * Method that gets multipls artifacts based on an inputted page and count.
     * @param count how many artifacts to display
     * @param page which page to display
     * @return list of Artifacts
     * @throws Exception
     */
    @Transactional
    public Iterable<Artifact> getManyArtifacts(int count, int page) throws Exception{
        ArrayList<Artifact> listOfArtifacts = (ArrayList<Artifact>) getAllArtifacts();
        ArrayList<Artifact> listToReturn = new ArrayList<Artifact>();
        if((page-1)*count > listOfArtifacts.size() || listOfArtifacts.size()==0){
            throw new Exception("no new artifacts to send");
        }else{
            for (int i = (page-1)*count; i<listOfArtifacts.size() && i<page*count; i++){
                listToReturn.add(listOfArtifacts.get(i));
            }
            return listToReturn;
        }
    }

    @Transactional
    public boolean deleteArtifact(long id){
        if(!artifactRepository.existsById(id)){
            return false;
        }
        Artifact artifact = artifactRepository.findById(id).get();
        if(artifact.hasLoan()) {
            loanRepository.deleteById(artifact.getLoan().getLoanId());
        }
        artifactRepository.deleteById(id);
        return true;
    }

    /**
     * retrieve an artifact with id
     * @param id of artifact
     * @return artifact
     * @throws Exception
     */
    @Transactional
    public Artifact retrieveArtifact(long id) throws Exception{
        Optional<Artifact> artifact = artifactRepository.findById(id);
        if(artifact.isEmpty()){
            throw new Exception();
        }else{
            return artifact.get();
        }
    }

    /**
     * method to save an artifact to the repository
     * @param artifact to save
     * @return artifact
     * @throws Exception
     */
    @Transactional
    public Artifact saveArtifact(Artifact artifact) throws Exception{
        if (artifact.getDescription().isBlank() || artifact.getName().isBlank() || artifact.getUrl().isBlank()){
            throw new Exception("artifact si missing crucial information");
        }else {
            return artifactRepository.save(artifact);
        }
    }
}
