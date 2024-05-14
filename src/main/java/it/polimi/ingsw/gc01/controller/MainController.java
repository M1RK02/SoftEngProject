package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.ColorAlreadyTakenException;
import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayersInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.stream.Collectors;


public class MainController implements Remote{

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
    public synchronized static MainController getInstance() {
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
     * @param nickname name of the player who wants to create the Room
     * @return the RoomController associated to the created Room
     * @throws RemoteException
     */
    public synchronized void createGame(String nickname) throws RemoteException{
        RoomController game = new RoomController();
        rooms.put(game.getRoomId(), game);
        try {
            game.addPlayer(nickname);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Join the room with the given id
     *
     * @param playerName the name of the player who wants to join the room
     * @param roomId the id of the room you want to join
     * @return the RoomController associated to the created Room if there is a joinable room, null otherwise
     * @throws RemoteException
     */
    public synchronized void joinGame(String playerName, String roomId) throws RemoteException, ColorAlreadyTakenException, MaxPlayersInException {
        if (rooms.get(roomId) != null){
            try{
                rooms.get(roomId).addPlayer(playerName);
                //return rooms.get(roomId);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        //return null;
    }

    /**
     * Join the first available room
     *
     * @param playerName the name of the player who wants to join the room
     * @return the RoomController associated to the created Room if there is a joinable room, null otherwise
     * @throws RemoteException
     */
    public synchronized void joinFirstGame(String playerName) throws RemoteException{
        for (String roomId : rooms.keySet()){
            if (rooms.get(roomId).getNumOfWaitingPlayers() < DefaultValue.MaxNumOfPlayer){
                try{
                    rooms.get(roomId).addPlayer(playerName);
                    //return rooms.get(roomId);
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
        //return null;
    }

    /**
     * Delete a room by its id
     *
     * @param roomId the id of the room to delete
     * @throws RemoteException
     */
    public void deleteRoom(String roomId) throws RemoteException{
        if (rooms.get(roomId) != null){
            rooms.remove(roomId);
        }
    }
}