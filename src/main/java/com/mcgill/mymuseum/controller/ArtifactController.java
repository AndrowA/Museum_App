package com.mcgill.mymuseum.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artifact")
public class ArtifactController {
    @Autowired
    ArtifactService artifactService;

    @GetMapping("/get/{id}")
    public ResponseEntity getArtifact(@PathVariable(name="id") long id){
        return new ResponseEntity("path variable is "+id,HttpStatus.OK);

        /*return artifactService.retrieveArtifact(id)
                .map(artifact -> new ResponseEntity<>(artifact, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
    }

    @GetMapping("/all/{page}/{count}")
    public ResponseEntity getManyArtifacts(@PathVariable(name="count") int count, @PathVariable(name="page") int page){
        return new ResponseEntity("path variables are count: " + count + ", page: " + page, HttpStatus.OK);
    }

    @PostMapping("/add") // to create an artifact
    public ResponseEntity addArtifact(@RequestBody String body){
        ObjectMapper mapper = new ObjectMapper();
        try {
            // map the json body sent by the request to the artifact class
            Artifact newArtifact = mapper.readValue(body, Artifact.class);
            Artifact savedArtifact = artifactService.saveArtiact(newArtifact);
            return new ResponseEntity<>(savedArtifact, HttpStatus.CREATED); // all good
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } /*catch (AuthorEmailNonUniqueException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); might do later, who knows
        }*/
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/modify/{id}")
    public ResponseEntity modifyArtifact(@PathVariable(name="id") long id, @RequestBody String body){
        return null;
    }

    @PostMapping("/assign/room/{id}")
    public ResponseEntity assignRoom(@PathVariable(name="id") long roomId, @RequestBody String body){return null;}

    @GetMapping("/assign/room/info/{id}")
    public ResponseEntity getRoom(@PathVariable(name="id") long roomId, @RequestBody String body){return null;}


}
