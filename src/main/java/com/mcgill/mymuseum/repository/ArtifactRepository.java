package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Artifact;
import com.mcgill.mymuseum.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArtifactRepository extends CrudRepository<Artifact,Long> {
}
