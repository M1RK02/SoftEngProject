package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayersInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {
    Map<String, RoomController> rooms;
    MainController mainController;

    Player p1 = new Player("1", null);
    Player p2 = new Player("2", null);
    Player p3 = new Player("3", null);
    Player p4 = new Player("4", null);
    Player p5 = new Player("5", null);
    Player p6 = new Player("6", null);
    Player p7 = new Player("7", null);
    Player p8 = new Player("8", null);
    Player p9 = new Player("9", null);
    Player p10 = new Player("10", null);
    Player p11 = new Player("11", null);
    Player p12 = new Player("12", null);
    Player p13 = new Player("13", null);
    Player p14 = new Player("14", null);
    Player p15 = new Player("15", null);
    Player p16 = new Player("16", null);
    Player p17 = new Player("17", null);
    Player p18 = new Player("18", null);
    Player p19 = new Player("19", null);

    @BeforeEach
    void setup() throws RemoteException{
        mainController = MainController.getInstance();
        rooms = new HashMap<>();

    }

    /*
    @Test
    void JoinFirstGameTest() throws RemoteException{
        rooms = mainController.getRooms();

        //Creo tre stanze
        mainController.createGame(p1.getName());
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());
        mainController.createGame(p2.getName());
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());
        mainController.createGame(p3.getName());
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());

        assertEquals(3, rooms.size());

        mainController.joinFirstGame(p4.getName());
        assertEquals(2, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinFirstGame(p5.getName());
        assertEquals(3, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinFirstGame(p6.getName());
        assertEquals(4, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinFirstGame(p7.getName());
        assertEquals(2, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinFirstGame(p8.getName());
        assertEquals(3, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinFirstGame(p9.getName());
        assertEquals(4, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinFirstGame(p10.getName());
        assertEquals(2, rooms.get(2).getNumOfWaitingPlayers());

        mainController.leaveGame(p1, rooms.get(0).getWaitingRoom().getRoomId());
        assertEquals(3, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinFirstGame(p1.getName());
        assertEquals(4, rooms.get(0).getNumOfWaitingPlayers());

        mainController.leaveGame(p2, rooms.get(1).getWaitingRoom().getRoomId());
        assertEquals(3, rooms.get(1).getNumOfWaitingPlayers());
        mainController.leaveGame(p7, rooms.get(1).getWaitingRoom().getRoomId());
        assertEquals(2, rooms.get(1).getNumOfWaitingPlayers());
        mainController.leaveGame(p8, rooms.get(1).getWaitingRoom().getRoomId());
        assertEquals(1, rooms.get(1).getNumOfWaitingPlayers());
        mainController.leaveGame(p9, rooms.get(1).getWaitingRoom().getRoomId());

        assertEquals(2, mainController.getRooms().size());

        mainController.deleteRoom(rooms.get(0).getRoomId());
        mainController.deleteRoom(rooms.get(0).getRoomId());
        assertEquals(0, mainController.getRooms().size());
    }


    @Test
    void JoinById() throws RemoteException{
        rooms = mainController.getRooms();

        //Creo tre stanze
        mainController.createGame(p1.getName());
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());
        mainController.createGame(p2.getName());
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());
        mainController.createGame(p3.getName());
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());

        assertEquals(3, rooms.size());

        assertThrows(PlayerAlreadyInException.class, () -> mainController.joinGame(p1.getName(), rooms.get(0).getRoomId()));
        assertEquals(1, rooms.get(0).getNumOfWaitingPlayers());

        mainController.joinGame(p4.getName(), rooms.get(0).getRoomId());
        assertEquals(2, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinGame(p5.getName(), rooms.get(1).getRoomId());
        assertEquals(2, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinGame(p6.getName(), rooms.get(2).getRoomId());
        assertEquals(2, rooms.get(2).getNumOfWaitingPlayers());

        mainController.joinGame(p7.getName(), rooms.get(0).getRoomId());
        assertEquals(3, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinGame(p8.getName(), rooms.get(1).getRoomId());
        assertEquals(3, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinGame(p9.getName(), rooms.get(2).getRoomId());
        assertEquals(3, rooms.get(2).getNumOfWaitingPlayers());

        mainController.joinGame(p10.getName(), rooms.get(0).getRoomId());
        assertEquals(4, rooms.get(0).getNumOfWaitingPlayers());
        mainController.joinGame(p11.getName(), rooms.get(1).getRoomId());
        assertEquals(4, rooms.get(1).getNumOfWaitingPlayers());
        mainController.joinGame(p12.getName(), rooms.get(2).getRoomId());
        assertEquals(4, rooms.get(2).getNumOfWaitingPlayers());
        assertThrows(MaxPlayersInException.class, () -> mainController.joinGame("PlayerFull", rooms.get(0).getRoomId()));

        mainController.deleteRoom(rooms.get(0).getRoomId());
        mainController.deleteRoom(rooms.get(0).getRoomId());
        mainController.deleteRoom(rooms.get(0).getRoomId());
        assertEquals(0, mainController.getRooms().size());



    }

     */



}