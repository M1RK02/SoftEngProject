package it.polimi.ingsw.gc01.model.room;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    @Test
    void getRoomId() {
        Room testRoom = new Room();
        String roomId = testRoom.getRoomId();
        assertNotNull(roomId);
        System.err.println(roomId);
    }
}