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

    public Iterable<Artifact> getArtifacts(){
        return artifactRepository.findAll();
    }

    public Optional<Artifact> retrieveArtifact(long id){
        return artifactRepository.findById(id);
    }

    public Artifact saveArtiact(Artifact artifact){ return artifactRepository.save(artifact);}
}
