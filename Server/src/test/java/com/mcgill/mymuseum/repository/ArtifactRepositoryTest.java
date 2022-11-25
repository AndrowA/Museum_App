package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.DisplayRoom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArtifactRepositoryTest {

    @Autowired
    ArtifactRepository artifactRepository;

    @AfterEach
    public void clearDatabase() {
        artifactRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadArtifact() {
        //Create Objects
        String artifactName = "Golden mask of Tut";
        String artifactDescription = "the most famous and admired artifacts of ancient Egypt in history and the world. It is the funerary death mask of the Egyptian pharaoh King Tutankamun.";
        String artifactPictureURL="www.maskOfTut.com";
        Artifact artifact1 = new Artifact();
        artifact1.setName(artifactName);
        artifact1.setDescription(artifactDescription);
        artifact1.setUrl(artifactPictureURL);

        //Save Object
        artifact1 = artifactRepository.save(artifact1);
        Long id = artifact1.getArtifactId();

        //Read Object
        artifact1 = artifactRepository.findById(id).get();

        //Assert that object has correct attributes
        assertNotNull(artifact1);
        assertEquals(artifactName,artifact1.getName());
        assertEquals(artifactDescription,artifact1.getDescription());
        assertEquals(artifactPictureURL, artifact1.getUrl());
    }
}