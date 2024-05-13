package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
    List<RoomController> rooms;
    MainController mainController;

    @BeforeEach
    void setup() throws RemoteException{
        mainController = MainController.getInstance();
        rooms = new ArrayList<>();

    }

    @Test
    void JoinFirstGameTest() throws RemoteException{
        rooms = mainController.getRooms();

        //Creo tre stanze
        mainController.createRoom("Player1", PlayerColor.BLUE);
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());
        mainController.createRoom("Player2", PlayerColor.BLUE);
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());
        mainController.createRoom("Player3", PlayerColor.BLUE);
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());

        assertEquals(3, rooms.size());

        mainController.joinFirstRoom("Player4", PlayerColor.GREEN);
        assertEquals(2, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinFirstRoom("Player5", PlayerColor.RED);
        assertEquals(3, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinFirstRoom("Player6", PlayerColor.YELLOW);
        assertEquals(4, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinFirstRoom("Player7", PlayerColor.GREEN);
        assertEquals(2, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinFirstRoom("Player8", PlayerColor.RED);
        assertEquals(3, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinFirstRoom("Player9", PlayerColor.YELLOW);
        assertEquals(4, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinFirstRoom("Player10", PlayerColor.GREEN);
        assertEquals(2, rooms.get(2).getNumOfWaitingPlayers());

        mainController.leaveGame("Player1", rooms.get(0).getWaitingRoom().getRoomId());
        assertEquals(3, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinFirstRoom("Player1", PlayerColor.BLUE);
        assertEquals(4, rooms.get(0).getNumOfWaitingPlayers());

        mainController.leaveGame("Player2", rooms.get(1).getWaitingRoom().getRoomId());
        assertEquals(3, rooms.get(1).getNumOfWaitingPlayers());
        mainController.leaveGame("Player7", rooms.get(1).getWaitingRoom().getRoomId());
        assertEquals(2, rooms.get(1).getNumOfWaitingPlayers());
        mainController.leaveGame("Player8", rooms.get(1).getWaitingRoom().getRoomId());
        assertEquals(1, rooms.get(1).getNumOfWaitingPlayers());
        mainController.leaveGame("Player9", rooms.get(1).getWaitingRoom().getRoomId());

        assertEquals(2, mainController.getRooms().size());

        mainController.deleteRoom(rooms.get(0).getRoomId());
        mainController.deleteRoom(rooms.get(0).getRoomId());
        assertEquals(0, mainController.getRooms().size());
    }


    @Test
    void JoinById() throws RemoteException{
        rooms = mainController.getRooms();

        //Creo tre stanze
        mainController.createRoom("Player1", PlayerColor.BLUE);
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());
        mainController.createRoom("Player2", PlayerColor.BLUE);
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());
        mainController.createRoom("Player3", PlayerColor.BLUE);
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());

        assertEquals(3, rooms.size());

        mainController.joinRoom("Player4", PlayerColor.GREEN, rooms.get(0).getRoomId());
        assertEquals(2, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinRoom("Player5", PlayerColor.GREEN, rooms.get(1).getRoomId());
        assertEquals(2, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinRoom("Player6", PlayerColor.GREEN, rooms.get(2).getRoomId());
        assertEquals(2, rooms.get(2).getNumOfWaitingPlayers());

        mainController.joinRoom("Player7", PlayerColor.YELLOW, rooms.get(0).getRoomId());
        assertEquals(3, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinRoom("Player8", PlayerColor.YELLOW, rooms.get(1).getRoomId());
        assertEquals(3, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinRoom("Player9", PlayerColor.YELLOW, rooms.get(2).getRoomId());
        assertEquals(3, rooms.get(2).getNumOfWaitingPlayers());

        mainController.joinRoom("Player10", PlayerColor.RED, rooms.get(0).getRoomId());
        assertEquals(4, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinRoom("Player11", PlayerColor.RED, rooms.get(1).getRoomId());
        assertEquals(4, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinRoom("Player12", PlayerColor.RED, rooms.get(2).getRoomId());
        assertEquals(4, rooms.get(2).getNumOfWaitingPlayers());

        mainController.deleteRoom(rooms.get(0).getRoomId());
        mainController.deleteRoom(rooms.get(0).getRoomId());
        mainController.deleteRoom(rooms.get(0).getRoomId());
        assertEquals(0, mainController.getRooms().size());



    }


}