package it.polimi.ingsw.gc01.model.room;

import java.util.*;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.ObserverManager;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.VirtualView;

public class WaitingRoom {
    private final String roomId;
    private List<Player> players;
    private List<PlayerColor> availableColors;
    private final ObserverManager notifier;

    public WaitingRoom() {
        roomId = generateRoomId();
        players = new ArrayList<Player>();
        availableColors = new ArrayList<>(Arrays.asList(PlayerColor.values()));
        notifier = new ObserverManager();
    }

    /**
     * Randomly generates a room id
     * @return a 5 character string
     */
    private String generateRoomId() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String alphaNumeric = alphabet + numbers;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 5;
        for(int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public String getRoomId(){
        return roomId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<PlayerColor> getAvailableColors() {
        return availableColors;
    }

    public ObserverManager getNotifier() {
        return notifier;
    }

    public Player getPlayerByName(String playerName){
        for (Player player : players){
            if (player.getName().equals(playerName)){
                return player;
            }
        }
        return null;
    }


    /**
     * Add the player to the waiting room (the check for max size will be done by the controller)
     * @param playerName chosen player name
     *
     */
    public void addPlayer(String playerName, VirtualView client){
        players.add(new Player(playerName, notifier));
        notifier.addObserver(playerName, client);
        notifier.updateRoomId(playerName, roomId);
        notifier.showAvailableColor(playerName, availableColors);
    }

    /**
     * Remove a player from the waiting room
     *
     * @param player the player to remove
     */
    public void removePlayer(Player player){
        players.remove(player);
        notifier.removeObserver(player.getName());
        notifier.serviceMessage(player.getName() + " left the room");
    }

    /**
     *
     * @return the num of players waiting in the Waiting room
     */
    public int getNumOfPlayers(){
        return this.getPlayers().size();
    }

    /**
     *
     * @return true if there are at least 2 players and they are all ready to start, else false
     */
    public boolean readyToStart(){
        //If every player is ready, the game starts
        return players.stream().filter(Player::getReady)
                .count() == players.size() && players.size() >= DefaultValue.MinNumOfPlayer;
    }
}