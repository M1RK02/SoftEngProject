package it.polimi.ingsw.gc01.model.room;

import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.gc01.model.player.*;

public class WaitingRoom {
    private List<Player> players;
    private List<PlayerColor> availableColors;
    private final String roomId;

    public WaitingRoom() {
        roomId = generateRoomId();
        players = new ArrayList<Player>();
        availableColors = new ArrayList<>(Arrays.asList(PlayerColor.values()));
    }

    public List<PlayerColor> getAvailableColors() {
        return availableColors;
    }

    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Add the player to the waiting room (the check for max size will be done by the controller)
     * @param playerName chosen player name
     */
    public void addPlayer(String playerName){
        players.add(new Player(playerName));
    }


    public int getNumOfPlayers(){
        return this.getPlayers().size();
    }

    public String getRoomId(){
        return roomId;
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

    /**
     * Remove a player from the waiting room
     *
     * @param player the player to remove
     */
    public void removePlayer(Player player){
        players.remove(player);
    }

}