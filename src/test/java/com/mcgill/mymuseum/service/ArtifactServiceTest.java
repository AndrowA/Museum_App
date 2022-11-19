package com.mcgill.mymuseum.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.DisplayRoom;
import com.mcgill.mymuseum.model.Loan;
import com.mcgill.mymuseum.model.Room;
import com.mcgill.mymuseum.repository.ArtifactRepository;
import com.mcgill.mymuseum.repository.LoanRepository;
import com.mcgill.mymuseum.repository.RoomRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @InjectMocks
    ArtifactService artifactService;

    @Nested
    @DisplayName("testing getAllArtifacts method")
    class GetAllArtifacts{
        @Test
        void listIsNull() {
            Mockito.when(artifactRepository.findAll()).thenReturn(Collections.emptyList());
            assertEquals(Collections.emptyList(), artifactService.getAllArtifacts());
            Mockito.verify(artifactRepository).findAll();
        }

        @Test
        void listIsFull() {
            List<Artifact> listOfArtifacts = new ArrayList<Artifact>();
            Mockito.when(artifactRepository.findAll()).thenReturn(listOfArtifacts);
            assertEquals(listOfArtifacts, artifactService.getAllArtifacts());
            Mockito.verify(artifactRepository).findAll();
        }
    }



    @Nested
    @DisplayName("testing retrieveArtifact method")
    class RetrieveArtifact{

        @Test
        @DisplayName("should throw exception when id does not exist")
        void shouldThrowException_whenIdDoesntExist() {
            long id = 5l;
            Mockito.when(artifactRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
            assertThrows(Exception.class , ()->artifactService.retrieveArtifact(id));
            Mockito.verify(artifactRepository).findById(id);
        }

        @Test
        @DisplayName("should return the artifact with that id")
        void testRetrieveArtifact_HappyPath(){
            long id = 5l;
            try {
                Artifact artifact = new Artifact();
                Mockito.when(artifactRepository.findById(id)).thenReturn(Optional.of(artifact));

                assertEquals(artifact, artifactService.retrieveArtifact(id));
                Mockito.verify(artifactRepository).findById(5l);
            }catch(Exception e){
                fail();
            }
        }
    }

    @Nested
    @DisplayName("testing saveArtifact method")
    class SaveArtifact{
        @Test
        @DisplayName("trying to save a null artifact")
        void saveEmptyArtifact(){
            Artifact artifact = new Artifact();
            assertThrows(Exception.class, ()-> artifactService.saveArtifact(artifact));
        }

        @Test
        @DisplayName("saving artifact successfully")
        void saveArtifact(){
            Artifact artifact = new Artifact();
            artifact.setName("hello");artifact.setDescription("hello");artifact.setUrl("hello");
            Mockito.when(artifactRepository.save(artifact)).thenReturn(artifact);
            try {
                assertEquals(artifact, artifactService.saveArtifact(artifact));
                Mockito.verify(artifactRepository).save(artifact);
            }catch(Exception e){
                fail();
            }
        }
    }

    @Nested
    @DisplayName("testing getManyArtifacts method")
    class GetManyArtifacts{
        @Test
        void noArtifacts(){
            Mockito.when(artifactRepository.findAll()).thenReturn(Collections.emptyList());
            assertThrows(Exception.class, ()->artifactService.getManyArtifacts(50,1));
        }

        @Test
        void lessThan50Artifacts(){
            try {
                ArrayList<Artifact> artifactArrayList = new ArrayList<>(25);
                for (int i = 0; i < 25; i++) {
                    artifactArrayList.add(new Artifact());
                }
                Mockito.when(artifactRepository.findAll()).thenReturn(artifactArrayList);
                assertEquals(artifactArrayList, artifactService.getManyArtifacts(50, 1));
            }catch(Exception e){
                fail();
            }
        }

        @Test
        void moreThan50Artifacts(){
            try {
                ArrayList<Artifact> artifactArrayList = new ArrayList<>(75);
                ArrayList<Artifact> artifactArrayList2 = new ArrayList<>(50);
                for (int i = 0; i < 75; i++) {
                    Artifact artifact = new Artifact();
                    if(i<50){
                        artifactArrayList2.add(artifact);
                    }
                    artifactArrayList.add(artifact);
                }
                Mockito.when(artifactRepository.findAll()).thenReturn(artifactArrayList);
                assertEquals(artifactArrayList2, artifactService.getManyArtifacts(50, 1));
            }catch(Exception e){
                fail();
            }
        }

        @Test
        void moreThan50ArtifactsSecondPage(){
            try {
                ArrayList<Artifact> artifactArrayList = new ArrayList<>(75);
                ArrayList<Artifact> artifactArrayList2 = new ArrayList<>(25);
                for (int i = 0; i < 75; i++) {
                    Artifact artifact = new Artifact();
                    if(i>=50){
                        artifactArrayList2.add(artifact);
                    }
                    artifactArrayList.add(artifact);
                }
                Mockito.when(artifactRepository.findAll()).thenReturn(artifactArrayList);
                assertEquals(artifactArrayList2, artifactService.getManyArtifacts(50, 2));
            }catch(Exception e){
                fail();
            }
        }
    }

}