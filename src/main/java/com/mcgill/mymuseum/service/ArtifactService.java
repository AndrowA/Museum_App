package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.repository.ArtifactRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

@Service
@Setter
public class ArtifactService {

    @Autowired
    ArtifactRepository artifactRepository;

    public Iterable<Artifact> getAllArtifacts(){
        return artifactRepository.findAll();
    }

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

    public Artifact retrieveArtifact(long id) throws Exception{
        Optional<Artifact> artifact = artifactRepository.findById(id);
        if(artifact.isEmpty()){
            throw new Exception();
        }else{
            return artifact.get();
        }
    }

    public Artifact saveArtifact(Artifact artifact) throws Exception{
        if (artifact.getDescription().isBlank() || artifact.getName().isBlank() || artifact.getUrl().isBlank()){
            throw new Exception("artifact si missing crucial information");
        }else {
            return artifactRepository.save(artifact);
        }
    }
}
