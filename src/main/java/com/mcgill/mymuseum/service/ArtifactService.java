package com.mcgill.mymuseum.service;

import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.repository.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtifactService {

    @Autowired
    ArtifactRepository artifactRepository;

    public Iterable<Artifact> getAllArtifacts(){
        return artifactRepository.findAll();
    }

    public Artifact retrieveArtifact(long id) throws Exception{
        Optional<Artifact> artifact = artifactRepository.findById(id);
        if(artifact.isEmpty()){
            throw new Exception();
        }else{
            return artifact.get();
        }
    }

    public Artifact saveArtiact(Artifact artifact){ return artifactRepository.save(artifact);}
}
