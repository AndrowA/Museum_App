package com.mcgill.mymuseum.controller;


import com.mcgill.mymuseum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {
    @Autowired
    ArtifactService artifactService;

    @GetMapping("/{id}")
    public ResponseEntity getArtifact(@PathVariable(name="id") Long id){
        return artifactService.retrieveArtifact(id)
                .map(artifact -> new ResponseEntity<>(artifact, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
