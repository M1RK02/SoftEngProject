package it.polimi.ingsw.gc01.model.room;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.decks.*;
import it.polimi.ingsw.gc01.model.player.Player;

public class Room {
    private String roomId;
    private Deck goldenDeck;
    private Deck resourceDeck;
    private Deck objectiveDeck;
    private List<Player> players;
    private Player currentPlayer;

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

    public String getRoomId() {
        return roomId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Return the next player
     * @return the next player
     */
    public Player getNextPlayer() {
        int currentPlayerIndex = players.indexOf(getCurrentPlayer());
        return currentPlayerIndex == players.size() - 1 ? players.get(0) : players.get(currentPlayerIndex+1);
    }

    /**
     * Return the list of drawable cards: the four in the center + the two on top of the decks
     * @return the list of drawable cards
     */
    public List<PlayableCard> getDrawableCards() {
        // TODO Serve sapere come funzionano i deck
        return null;
    }

    /**
     * Get the game winner
     * @return winner player
     */
    public Player getWinner() {
        Player winner = players.stream().max(Comparator.comparingInt(Player::getScore)).orElse(null);
        if (winner != null) {
            return winner;
        }
        return null;
        // TODO Deve fare qualcosa (tipo lanciare un'eccezione) se ritorna null (unico motivo per cui potrebbe ritornare null è se non ci sono giocatori)
    }

    /**
     * Calculate the points for the selected player
     * @param player to calculate points
     * @return points of the player
     */
    public int calculatePoints(Player player) {
        // TODO Serve sapere come controllare gli obiettivi
        return 0;
    }

    /**
     * End the game
     */
    public void endGame() {
        // TODO Va fatto alla fine
    }
}