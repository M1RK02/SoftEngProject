package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.MaxPlayersInException;
import it.polimi.ingsw.gc01.controller.exceptions.PlayerAlreadyInException;
import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.PlayerColor;

import java.util.ArrayList;
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
    private List<RoomController> rooms;


    private MainController(){
        rooms = new ArrayList<>();
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

    /**
     * Create a new room
     *
     * @param nickname name of the player who wants to create the Room
     * @param color color of the player's pawn who wants to create the Room
     * @return the RoomController associated to the created Room
     * @throws RemoteException
     */
    public synchronized RoomController createRoom(String nickname, PlayerColor color) throws RemoteException{
        RoomController game = new RoomController();
        rooms.add(game);
        try {
            game.addPlayer(nickname, color);
        } catch (MaxPlayersInException | PlayerAlreadyInException e){
            e.printStackTrace(System.out);
        }
        return game;
    }

    /**
     * Join the first available room
     *
     * @param nickname the name of the player who wants to join the room
     * @param color the color of the player's pawn who wants to join the room
     * @return the RoomController associated to the created Room if there is a joinable room, null otherwise
     * @throws RemoteException
     */
    public synchronized RoomController joinFirstRoom(String nickname, PlayerColor color) throws RemoteException{
        List<RoomController> availableGames = rooms.stream().filter(x -> (x.getState().equals(GameState.WAITING) && x.getNumOfWaitingPlayers() < DefaultValue.MaxNumOfPlayer)).collect(Collectors.toList());
        if (!availableGames.isEmpty()){
            try {
                availableGames.get(0).addPlayer(nickname, color);
                return availableGames.get(0);
            } catch (PlayerAlreadyInException e){
                e.printStackTrace(System.out);
            }
        }
        return null;
    }

    /**
     * Join the room with the given id
     *
     * @param nickname the name of the player who wants to join the room
     * @param color the color of the player's pawn who wants to join the room
     * @param roomId the id of the room you want to join
     * @return the RoomController associated to the created Room if there is a joinable room, null otherwise
     * @throws RemoteException
     */
    public synchronized RoomController joinRoom(String nickname, PlayerColor color, String roomId) throws RemoteException{
        List<RoomController> game = rooms.stream().filter(x -> (x.getRoomId().equals(roomId))).collect(Collectors.toList());
        if (game.size() == 1){
            try {
                game.get(0).addPlayer(nickname, color);
                return game.get(0);
            } catch (MaxPlayersInException | PlayerAlreadyInException e){
                e.printStackTrace(System.out);
            }
        }
        return null;
    }

    /**
     * Leave a player from a game
     *
     * @param nickname the name of the player who wants to leave
     * @param roomId the id of the room to leave
     * @throws RemoteException
     */
    public synchronized void leaveGame(String nickname, String roomId) throws RemoteException{
        List<RoomController> game = rooms.stream().filter(x -> (x.getRoomId().equals(roomId))).collect(Collectors.toList());
        if (game.size() == 1){
            game.get(0).leave(nickname);
        }

        //Se non ci sono più player elimina la stanza
        if (game.get(0).getState() == GameState.WAITING){
            if (game.get(0).getNumOfWaitingPlayers() == 0){
                deleteRoom(game.get(0).getRoomId());
            }
        }
        else {
            if (game.get(0).getNumOfPlayers() == 0){
                deleteRoom(game.get(0).getRoomId());
            }
        }
    }

    /**
     * Delete a room by its id
     *
     * @param roomId the id of the room to delete
     * @throws RemoteException
     */
    public void deleteRoom(String roomId) throws RemoteException{
        List<RoomController> gameToRemove = rooms.stream().filter(x -> (x.getRoomId().equals(roomId))).collect(Collectors.toList());
        if (gameToRemove.size() == 1){
            rooms.remove(gameToRemove.get(0));
        }
    }

    public List<RoomController> getRooms() {
        return rooms;
    }
}
