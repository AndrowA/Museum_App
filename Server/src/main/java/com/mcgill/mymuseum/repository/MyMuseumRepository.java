package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.MyMuseum;
import org.springframework.data.repository.CrudRepository;

public interface MyMuseumRepository extends CrudRepository<MyMuseum,Long> {
}