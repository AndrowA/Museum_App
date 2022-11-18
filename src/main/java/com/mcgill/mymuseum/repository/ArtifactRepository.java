package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Artifact;
import org.springframework.data.repository.CrudRepository;

public interface ArtifactRepository extends CrudRepository<Artifact,Long> {

}
