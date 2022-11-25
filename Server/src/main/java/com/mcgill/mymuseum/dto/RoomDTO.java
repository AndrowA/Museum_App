package com.mcgill.mymuseum.dto;

import com.mcgill.mymuseum.model.DisplayRoom;
import com.mcgill.mymuseum.model.Room;

public class RoomDTO {
    public final Long id;
    public final int capacity;

    public RoomDTO(Room room) {
        this.id = room.getRoomId();
        if (room instanceof DisplayRoom){
            this.capacity = 500-((DisplayRoom) room).getRoomCapacity(); //place left until full
        }else {
            this.capacity = 0;
        }
    }
}
