package it.polimi.ingsw.gc01.model.room;

import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.network.ObserverManager;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    private static Room testRoom;
    private static List<Player> testPlayers;

    @BeforeAll
    static void setup() {
        testPlayers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            testPlayers.add(new Player("Player" + i, null));
        }
        testRoom = new Room("testId", testPlayers, new ObserverManager());
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

    @Test
    void getWinnerByTotalPoints() {
        testPlayers.get(0).addPoints(1);
        testPlayers.get(0).addObjectivePoints(1);
        assertEquals(testPlayers.get(0), testRoom.getWinners().get(0));
        testPlayers.get(0).addPoints(-1);
        testPlayers.get(0).addObjectivePoints(-1);
    }

    @Test
    void getWinnerByObjectivePoints() {
        testPlayers.get(0).addPoints(2);
        testPlayers.get(0).addObjectivePoints(1);
        testPlayers.get(1).addPoints(1);
        testPlayers.get(1).addObjectivePoints(2);
        assertEquals(testPlayers.get(1), testRoom.getWinners().get(0));
        testPlayers.get(0).addPoints(-2);
        testPlayers.get(0).addObjectivePoints(-1);
        testPlayers.get(1).addPoints(-1);
        testPlayers.get(1).addObjectivePoints(-2);
    }

    @Test
    void getWinnersIfMultiple() {
        testPlayers.get(0).addPoints(1);
        testPlayers.get(0).addObjectivePoints(1);
        testPlayers.get(1).addPoints(1);
        testPlayers.get(1).addObjectivePoints(1);
        assertEquals(List.of(testPlayers.get(0), testPlayers.get(1)), testRoom.getWinners());
        testPlayers.get(0).addPoints(-1);
        testPlayers.get(0).addObjectivePoints(-1);
        testPlayers.get(1).addPoints(-1);
        testPlayers.get(1).addObjectivePoints(-1);
    }
}