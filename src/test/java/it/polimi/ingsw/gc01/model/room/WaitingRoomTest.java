package it.polimi.ingsw.gc01.model.room;

import it.polimi.ingsw.gc01.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaitingRoomTest {
    private WaitingRoom waitingRoom;
    Player p1 = new Player("Player1", null);
    Player p2 = new Player("Player2", null);
    Player p3 = new Player("Player3", null);
    Player p4 = new Player("Player4", null);
    @BeforeEach
    void setup(){
        waitingRoom = new WaitingRoom();
    }

    @Test
    void getRoomId() {
        assertNotNull(waitingRoom.getRoomId());
        assertEquals(5, waitingRoom.getRoomId().length());
    }

    @Test
    void getPlayers() {
        assertEquals(0, waitingRoom.getPlayers().size());
    }

    @Test
    void addPlayer() {
        assertEquals(0, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p1.getName());
        assertEquals(1, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p2.getName());
        assertEquals(2, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p3.getName());
        assertEquals(3, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p4.getName());
        assertEquals(4, waitingRoom.getPlayers().size());
    }

    @Test
    void removePlayer() {
        assertEquals(0, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p1.getName());
        waitingRoom.addPlayer(p2.getName());
        waitingRoom.addPlayer(p3.getName());
        waitingRoom.addPlayer(p4.getName());
        assertEquals(4, waitingRoom.getPlayers().size());
        waitingRoom.removePlayer(p1);
        assertEquals(3, waitingRoom.getPlayers().size());
        waitingRoom.removePlayer(p2);
        assertEquals(2, waitingRoom.getPlayers().size());
        waitingRoom.removePlayer(p3);
        assertEquals(1, waitingRoom.getPlayers().size());
        waitingRoom.removePlayer(p4);
        assertEquals(0, waitingRoom.getPlayers().size());
    }

    @Test
    void readyToStart() {
        waitingRoom.addPlayer(p1.getName());
        waitingRoom.getPlayers().get(0).switchReady();
        assertFalse(waitingRoom.readyToStart());
        waitingRoom.addPlayer(p2.getName());
        assertFalse(waitingRoom.readyToStart());
        waitingRoom.getPlayers().get(1).switchReady();
        assertTrue(waitingRoom.readyToStart());
        waitingRoom.addPlayer(p3.getName());
        waitingRoom.addPlayer(p4.getName());
        assertFalse(waitingRoom.readyToStart());
        waitingRoom.getPlayers().get(2).switchReady();
        waitingRoom.getPlayers().get(3).switchReady();
        assertTrue(waitingRoom.readyToStart());
    }
}