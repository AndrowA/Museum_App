package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.MuseumPass;
import com.mcgill.mymuseum.model.Visitor;
import org.springframework.data.repository.CrudRepository;

public interface MuseumPassRepository extends CrudRepository<MuseumPass,Long> {
    public Iterable<MuseumPass> findMuseumPassByOwner(Visitor visitor);
}
