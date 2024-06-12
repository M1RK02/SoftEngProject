package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.GameInProgressException;
import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayersInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.VirtualViewStub;
import it.polimi.ingsw.gc01.network.ObserverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomControllerWaitingTest {

    private static VirtualViewStub client;
    private static ObserverManager observer;
    private static RoomController controller;
    private static List<String> playerNames;
    @BeforeEach
    void setup(){
        controller = new RoomController();
        client = new VirtualViewStub();
        observer = new ObserverManager();
        playerNames = new ArrayList<>();
        playerNames.add("p1");
        playerNames.add("p2");
        playerNames.add("p3");
        playerNames.add("p4");
        playerNames.add("p5");
    }

    @Test
    void addPlayerAndLeave(){
        assertEquals(0, controller.getWaitingRoom().getPlayers().size());
        controller.addPlayer(playerNames.get(0), client);
        assertEquals(1, controller.getWaitingRoom().getPlayers().size());
        assertThrows(PlayerAlreadyInException.class, () -> controller.addPlayer(playerNames.get(0), client));
        controller.addPlayer(playerNames.get(1), client);
        assertEquals(2, controller.getWaitingRoom().getPlayers().size());
        controller.addPlayer(playerNames.get(2), client);
        assertEquals(3, controller.getWaitingRoom().getPlayers().size());
        controller.addPlayer(playerNames.get(3), client);
        assertEquals(4, controller.getWaitingRoom().getPlayers().size());
        assertThrows(MaxPlayersInException.class, () -> controller.addPlayer(playerNames.get(4), client));
        controller.setState(GameState.RUNNING);
        assertThrows(GameInProgressException.class, () -> controller.addPlayer(playerNames.get(4), client));
        controller.setState(GameState.WAITING);
        controller.leave(playerNames.get(0));
        assertEquals(3, controller.getWaitingRoom().getPlayers().size());
        controller.leave(playerNames.get(1));
        assertEquals(2, controller.getWaitingRoom().getPlayers().size());
        controller.leave(playerNames.get(2));
        assertEquals(1, controller.getWaitingRoom().getPlayers().size());
        controller.leave(playerNames.get(3));
        assertEquals(0, controller.getWaitingRoom().getPlayers().size());
    }


    @Test
    void switchReadyAndStart(){
        assertNull(controller.getRoom());
        controller.addPlayer(playerNames.get(0), client);
        assertFalse(controller.getWaitingRoom().getPlayerByName(playerNames.get(0)).isReady());
        controller.switchReady(playerNames.get(0));
        assertTrue(controller.getWaitingRoom().getPlayerByName(playerNames.get(0)).isReady());
        assertNull(controller.getRoom());

        controller.addPlayer(playerNames.get(1), client);
        assertFalse(controller.getWaitingRoom().getPlayerByName(playerNames.get(1)).isReady());
        controller.switchReady(playerNames.get(1));
        assertTrue(controller.getWaitingRoom().getPlayerByName(playerNames.get(1)).isReady());
        assertNotNull(controller.getRoom());
        assertEquals(GameState.STARTER_SELECTION, controller.getState());
        assertEquals(1, controller.getRoom().getPlayers().get(0).getHand().size());
        controller.leave(playerNames.get(0));
        assertEquals(1, controller.getWaitingRoom().getPlayers().size());
        controller.leave(playerNames.get(1));
        assertEquals(0, controller.getWaitingRoom().getPlayers().size());

    }




}