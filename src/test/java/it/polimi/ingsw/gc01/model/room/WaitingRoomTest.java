package it.polimi.ingsw.gc01.model.room;

import it.polimi.ingsw.gc01.model.VirtualViewStub;
import it.polimi.ingsw.gc01.model.player.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class WaitingRoomTest {
    private WaitingRoom waitingRoom;
    private Player p1, p2, p3, p4;

    @BeforeEach
    void setup() {
        waitingRoom = new WaitingRoom();
        p1 = new Player("Player1", waitingRoom.getNotifier());
        p2 = new Player("Player2", waitingRoom.getNotifier());
        p3 = new Player("Player3", waitingRoom.getNotifier());
        p4 = new Player("Player4", waitingRoom.getNotifier());
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
        waitingRoom.addPlayer(p1.getName(), new VirtualViewStub());
        assertEquals(1, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p2.getName(), new VirtualViewStub());
        assertEquals(2, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p3.getName(), new VirtualViewStub());
        assertEquals(3, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p4.getName(), new VirtualViewStub());
        assertEquals(4, waitingRoom.getPlayers().size());
    }

    @Test
    void removePlayer() {
        assertEquals(0, waitingRoom.getPlayers().size());
        waitingRoom.addPlayer(p1.getName(), new VirtualViewStub());
        waitingRoom.addPlayer(p2.getName(), new VirtualViewStub());
        waitingRoom.addPlayer(p3.getName(), new VirtualViewStub());
        waitingRoom.addPlayer(p4.getName(), new VirtualViewStub());
        assertEquals(4, waitingRoom.getPlayers().size());
        waitingRoom.removePlayer(p1.getName());
        assertEquals(3, waitingRoom.getPlayers().size());
        waitingRoom.removePlayer(p2.getName());
        assertEquals(2, waitingRoom.getPlayers().size());
        waitingRoom.removePlayer(p3.getName());
        assertEquals(1, waitingRoom.getPlayers().size());
        waitingRoom.removePlayer(p4.getName());
        assertEquals(0, waitingRoom.getPlayers().size());
    }

    @Test
    void readyToStart() {
        waitingRoom.addPlayer(p1.getName(), new VirtualViewStub());
        waitingRoom.getPlayers().get(0).switchReady();
        assertFalse(waitingRoom.readyToStart());
        waitingRoom.addPlayer(p2.getName(), new VirtualViewStub());
        assertFalse(waitingRoom.readyToStart());
        waitingRoom.getPlayers().get(1).switchReady();
        assertTrue(waitingRoom.readyToStart());
        waitingRoom.addPlayer(p3.getName(), new VirtualViewStub());
        waitingRoom.addPlayer(p4.getName(), new VirtualViewStub());
        assertFalse(waitingRoom.readyToStart());
        waitingRoom.getPlayers().get(2).switchReady();
        waitingRoom.getPlayers().get(3).switchReady();
        assertTrue(waitingRoom.readyToStart());
    }
}