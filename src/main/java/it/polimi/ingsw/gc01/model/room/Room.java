package it.polimi.ingsw.gc01.model.room;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.decks.*;
import it.polimi.ingsw.gc01.model.player.*;

public class Room {
    private final String roomId;
    private final List<Player> players;
    private Player currentPlayer;
    private GoldenDeck goldenDeck;
    private ResourceDeck resourceDeck;
    private ObjectiveDeck objectiveDeck;
    private StarterDeck starterDeck;
    private List<PlayableCard> visibleCards;
    private List<ObjectiveCard> commonObjectives;

    public Room(List<Player> players) {
        roomId = generateRoomId();
        this.players = players;
        currentPlayer = players.get(0);
        goldenDeck = new GoldenDeck();
        resourceDeck = new ResourceDeck();
        objectiveDeck = new ObjectiveDeck();
        starterDeck = new StarterDeck();
        visibleCards = new ArrayList<>();
        commonObjectives = new ArrayList<>();
        initTable();
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
     * initiate the common playing field (decks and drawable cards)
     */
    private void initTable() {
        goldenDeck.shuffle();
        resourceDeck.shuffle();
        objectiveDeck.shuffle();
        starterDeck.shuffle();
        visibleCards.add(goldenDeck.pick());
        visibleCards.add(goldenDeck.pick());
        visibleCards.add(resourceDeck.pick());
        visibleCards.add(resourceDeck.pick());
        commonObjectives.add(objectiveDeck.pick());
        commonObjectives.add(objectiveDeck.pick());
    }

    public StarterDeck getStarterDeck() {
        return starterDeck;
    }

    public ResourceDeck getResourceDeck() {
        return resourceDeck;
    }

    public GoldenDeck getGoldenDeck() {
        return goldenDeck;
    }

    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }

    public List<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
    }

    public List<PlayableCard> getVisibleCards() {
        return visibleCards;
    }

    public String getRoomId() {
        return roomId;
    }

    public List<Player> getPlayers() {
        return players;
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
        List<PlayableCard> drawableCards = new ArrayList<>(visibleCards);
        drawableCards.add(goldenDeck.get());
        drawableCards.add(resourceDeck.get());
        return drawableCards;
    }

    /**
     * Get the game winner
     * @return winner player
     */
    public Player getWinner() {
        return players.stream().max(Comparator.comparingInt(Player::getPoints)).orElse(null);
    }
}