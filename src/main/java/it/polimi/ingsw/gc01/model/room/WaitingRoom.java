package it.polimi.ingsw.gc01.model.room;

import java.util.*;
import it.polimi.ingsw.gc01.model.player.*;

public class WaitingRoom {
    private List<Player> players;
    private List<PlayerColor> availableColors;

    public WaitingRoom() {
        players = new ArrayList<Player>();
        availableColors = new ArrayList<>(Arrays.asList(PlayerColor.values()));
    }

    public List<PlayerColor> getAvailableColors() {
        return availableColors;
    }

    /**
     * Add the player to the waiting room (the check for max size will be done by the controller)
     * @param playerName chosen player name
     * @param playerColor chosen color
     */
    public void addPlayer(String playerName, PlayerColor playerColor){
        players.add(new Player(playerName, playerColor));
        availableColors.remove(playerColor);
    }

    public List<Player> getPlayers() {
        return players;
    }

}