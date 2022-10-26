package com.mcgill.mymuseum.repository;

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

        //Save Object

        //Read Object

        //Assert that object has correct attributes
    }
}