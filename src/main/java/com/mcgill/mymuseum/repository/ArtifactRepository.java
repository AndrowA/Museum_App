package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Artifact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;

public interface ArtifactRepository extends CrudRepository<Artifact,Long> {
    Iterable<Artifact> findArtifactsByPageAndNumber(int amount, Pageable page);
    Artifact modifyArtifact(int id, Artifact newArtifact);

}
