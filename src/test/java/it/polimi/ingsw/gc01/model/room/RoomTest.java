package it.polimi.ingsw.gc01.model.room;

import java.util.*;
import it.polimi.ingsw.gc01.model.player.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    private static Room testRoom;
    private static List<Player> testPlayers;

    @BeforeAll
     static void beforeAll() {
        testPlayers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            testPlayers.add(new Player("Player" + i, PlayerColor.values()[i]));
        }
        testRoom = new Room(testPlayers);
    }

    @Test
    void getRoomId() {
        String roomId = testRoom.getRoomId();
        assertNotNull(roomId);
        System.out.println("The generated room id is: " + roomId);
    }

    @Test
    void getNextPlayer() {
        testRoom.setCurrentPlayer(testPlayers.get(0));
        assertEquals(testRoom.getNextPlayer(), testPlayers.get(1));
    }

    @Test
    void getNextPlayerIfLast() {
        testRoom.setCurrentPlayer(testPlayers.get(3));
        assertEquals(testRoom.getNextPlayer(), testPlayers.get(0));
    }
}