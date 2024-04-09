package it.polimi.ingsw.gc01.model.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.decks.Deck;
import it.polimi.ingsw.gc01.model.decks.DeckType;
import it.polimi.ingsw.gc01.model.player.Player;

public class Room {
    private String roomId;
    private Deck goldenDeck;
    private Deck resourceDeck;
    private Deck objectiveDeck;
    private List<Player> players;
    private Player currentPlayer;

    /**
     * Room constructor
     */
    public Room() {
        roomId = generateRoomId();
        goldenDeck = new Deck(DeckType.GOLDEN);
        resourceDeck = new Deck(DeckType.RESOURCE);
        objectiveDeck = new Deck(DeckType.OBJECTIVE);
        players = new ArrayList<Player>();
    }

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
     * Get the room id
     * @return the room id (string)
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Return the players
     * @return the list of players in the game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Return the next player
     * @return the next player
     */
    public Player getNextPlayer() {
        return null;
    }

    /**
     * Get the current player
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Return the list of drawable cards: the four in the center + the two on top of the decks
     * @return the list of drawable cards
     */
    public List<PlayableCard> getDrawableCards() {
        return null;
    }

    /**
     * Get the game winner
     * @return winner player
     */
    public Player getWinner() {
        return null;
    }

    /**
     * Set whose turn is it
     * @param currentPlayer
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Calculate the points for the selected player
     * @param player to calculate points
     * @return points of the player
     */
    public int calculatePoints(Player player) {
        return 0;
    }

    /**
     * End game
     */
    public void endGame() {
    }
}
