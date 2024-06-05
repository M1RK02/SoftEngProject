package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.RemoteException;
import java.util.*;

public class MainController {
    /**
     * Singleton Pattern, instance of the class
     */
    private static MainController instance = null;

    /**
     * List of existing rooms
     */
    private final Map<String, RoomController> rooms = new HashMap<>();

    private MainController() {
    }

    /**
     * Singleton Pattern
     *
     * @return the only one instance of the MainController class
     */
    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    /**
     *
     * @return the map of roomId and RoomController
     */
    public Map<String, RoomController> getRooms() {
        return rooms;
    }

    /**
     * Create a new room
     *
     * @param playerName name of the player who wants to create the Room
     * @param client     reference to the client
     */
    public void createGame(String playerName, VirtualView client) {
        RoomController roomController = new RoomController();
        rooms.put(roomController.getRoomId(), roomController);
        roomController.addPlayer(playerName, client);
    }

    /**
     * Join the room with the given id
     *
     * @param playerName the name of the player who wants to join the room
     * @param client     reference to the client
     * @param roomId     the id of the room you want to join
     */
    public void joinGame(String playerName, VirtualView client, String roomId) {
        String error = "";
        if (rooms.get(roomId) != null) {
            try {
                rooms.get(roomId).addPlayer(playerName, client);
            } catch (Exception e) {
                error = e.getMessage();
            }
        } else {
            error = "MAIN No room with this id exists";
        }
        if (!error.isEmpty()) {
            sendErrorToClient(client, error);
        }
    }

    /**
     * Join the first available room
     *
     * @param playerName the name of the player who wants to join the room
     * @param client     reference to the client
     */
    public void joinFirstGame(String playerName, VirtualView client) {
        boolean joined = false;
        Iterator<RoomController> iterator = rooms.values().iterator();
        while (!joined && iterator.hasNext()) {
            RoomController room = iterator.next();
            try {
                room.addPlayer(playerName, client);
                joined = true;
            } catch (Exception ignored) {
            }
        }
        if (!joined) {
            sendErrorToClient(client, "MAIN No rooms available");
        }
    }

    private void sendErrorToClient(VirtualView client, String error) {
        try {
            client.showError(error);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete a room by its id
     *
     * @param roomId the id of the room to delete
     */
    public void deleteRoom(String roomId) {
        rooms.remove(roomId);
    }
}