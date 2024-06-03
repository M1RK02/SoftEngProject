package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.VirtualViewStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
    private static MainController mainController = MainController.getInstance();

    @Test
    void CreateGameTest() {
        VirtualViewStub client = new VirtualViewStub();
        mainController.createGame("p1", client);
        assertEquals(mainController.getRooms().keySet().toArray()[0], client.roomId);
        assertEquals(1, mainController.getRooms().get(client.roomId).getWaitingRoom().getPlayers().size());
    }

    @Test
    void JoinFirstGameTest() {
        VirtualViewStub client1 = new VirtualViewStub();
        mainController.joinFirstGame("p1", client1);
        assertEquals("MAIN No rooms available", client1.error);

        mainController.createGame("p1", client1);

        VirtualViewStub client2 = new VirtualViewStub();
        mainController.joinFirstGame("p2", client2);
        assertEquals(client1.roomId, client2.roomId);
        assertEquals(2, mainController.getRooms().get(client1.roomId).getWaitingRoom().getPlayers().size());
    }

    @Test
    void JoinGameTest() {
        VirtualViewStub client1 = new VirtualViewStub();
        mainController.joinGame("p1", client1, "LAZZZ");
        assertEquals("MAIN No room with this id exists", client1.error);

        mainController.createGame("p1", client1);

        VirtualViewStub client2 = new VirtualViewStub();
        mainController.joinGame("p2", client2, client1.roomId);
        assertEquals(client1.roomId, client2.roomId);
        assertEquals(2, mainController.getRooms().get(client1.roomId).getWaitingRoom().getPlayers().size());
    }

    @AfterEach
    void removeAllRooms() {
        mainController.getRooms().clear();
    }
}