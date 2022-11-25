package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.MuseumPass;
import org.springframework.data.repository.CrudRepository;

public interface MuseumPassRepository extends CrudRepository<MuseumPass,Long> {
}
