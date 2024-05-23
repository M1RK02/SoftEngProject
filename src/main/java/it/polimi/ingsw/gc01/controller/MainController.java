package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    /**
     * Singleton Pattern, instance of the class
     */
    private static MainController instance = null;

    /**
     * List of existing rooms
     */
    private Map<String, RoomController> rooms;

    private MainController(){
        rooms = new HashMap<>();
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

    public Map<String, RoomController> getRooms() {
        return rooms;
    }

    /**
     * Create a new room
     *
     * @param playerName name of the player who wants to create the Room
     * @param client reference to the client
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
     * @param client reference to the client
     * @param roomId the id of the room you want to join
     */
    public void joinGame(String playerName, VirtualView client, String roomId){
        String error = "";
        if (rooms.get(roomId) != null){
            try {
                rooms.get(roomId).addPlayer(playerName, client);
            } catch (Exception e){
                error = e.getMessage();
            }
        } else {
            error = "MAIN No room with this id exists";
        }
        if (!error.isEmpty()) {
            try {
                client.showError(error);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Join the first available room
     *
     * @param playerName the name of the player who wants to join the room
     * @param client reference to the client
     */
    public void joinFirstGame(String playerName, VirtualView client){
        boolean joined = false;
        for (RoomController room : rooms.values()){
            try {
                room.addPlayer(playerName, client);
                joined = true;
                break;
            } catch (Exception ignored) {}
        }
        if (!joined) {
            try {
                client.showError("MAIN No rooms available");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Delete a room by its id
     *
     * @param roomId the id of the room to delete
     */
    public void deleteRoom(String roomId) {
        if (rooms.get(roomId) != null){
            rooms.remove(roomId);
        }
    }
}