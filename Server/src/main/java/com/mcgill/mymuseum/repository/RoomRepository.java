package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room,Long> {
}
