package com.mcgill.mymuseum.repository;

import com.mcgill.mymuseum.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room,Long> {
}
