package it.polimi.ingsw.gc01.model.room;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;

public class WaitingRoom {
    private List<Player> players;
    private List<PlayerColor> availableColors;
    private Room room;

    public WaitingRoom() {
        players = new ArrayList<Player>();
        availableColors = new ArrayList<PlayerColor>();
        room = new Room();
    }

    /**
     * Start the game
     */
    public void startGame() {
    }

    /**
     * Add the new player to the list of players
     * @param newPlayer new player to add
     */
    public void addPlayer(Player newPlayer){
    }

    /**
     * Return the list of available colors
     * @return list of available colors
     */
    public List<PlayerColor> getAvailableColors() {
        return availableColors;
    }

    /**
     * Remove the color from the available colors
     * @param usedColor color chosen by a player
     */
    public void useColor(PlayerColor usedColor) {
        this.availableColors.remove(usedColor);
    }
}
