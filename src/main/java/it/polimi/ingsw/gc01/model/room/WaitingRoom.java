package it.polimi.ingsw.gc01.model.room;

import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.network.*;
import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.util.*;

/**
 * Class to manage the waiting room
 */
public class WaitingRoom {
    /**
     * Id of the room
     */
    private final String roomId;
    /**
     * List of players
     */
    private final List<Player> players;
    /**
     * Notifier object to communicate updates
     */
    private final ObserverManager notifier;

    /**
     * Constructor of the WaitingRoom
     */
    public WaitingRoom() {
        roomId = generateRoomId();
        players = new ArrayList<Player>();
        notifier = new ObserverManager();
    }

    /**
     * Randomly generates a room id
     *
     * @return a 5 character string
     */
    private String generateRoomId() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String alphaNumeric = alphabet + numbers;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 5;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    /**
     * @return the roomId
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * @return the list of the players that joined the Waiting Room
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return The observer manager associated with the room.
     */
    public ObserverManager getNotifier() {
        return notifier;
    }

    /**
     * Retrieves a player from the room by their name.
     *
     * @param playerName The name of the player to retrieve.
     * @return The player with the specified name
     */
    public Player getPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Add the player to the waiting room (the check for max size will be done by the controller)
     *
     * @param playerName chosen player name
     */
    public void addPlayer(String playerName, VirtualView client) {
        players.add(new Player(playerName, notifier));
        notifier.addObserver(playerName, client);
        notifier.updateRoomId(playerName, roomId);
        List<String> playerNames = players.stream().map(Player::getName).toList();
        notifier.showPlayers(playerName, playerNames);
        notifier.showPlayerJoined(playerName);
    }

    /**
     * Remove a player from the waiting room
     *
     * @param playerName the name of the player to remove
     */
    public void removePlayer(String playerName) {
        players.remove(getPlayerByName(playerName));
        notifier.removeObserver(playerName);
        notifier.showPlayerLeft(playerName);
    }

    /**
     * @return true if there are at least 2 players and they are all ready to start, else false
     */
    public boolean readyToStart() {
        //If every player is ready, the game starts
        return players.stream()
                .filter(Player::isReady)
                .count() == players.size() && players.size() >= DefaultValue.MinNumOfPlayer;
    }
}