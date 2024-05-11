package it.polimi.ingsw.gc01.model.room;

import java.util.*;
import java.util.stream.Collectors;

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
    private List<ObjectiveCard> commonObjectives;
    private Map<TablePosition, ResourceCard> drawableCards;

    public Room(List<Player> players, String roomId) {
        this.roomId = roomId;
        this.players = players;
        currentPlayer = players.get(0);
        goldenDeck = new GoldenDeck();
        resourceDeck = new ResourceDeck();
        objectiveDeck = new ObjectiveDeck();
        starterDeck = new StarterDeck();
        commonObjectives = new ArrayList<>();
        drawableCards = new HashMap<>();
        initTable();
    }

    /**
     * initiate the common playing field (decks and drawable cards)
     */
    private void initTable() {
        goldenDeck.shuffle();
        resourceDeck.shuffle();
        objectiveDeck.shuffle();
        starterDeck.shuffle();
        drawableCards.put(TablePosition.RESOURCEDECK, resourceDeck.pick());
        drawableCards.put(TablePosition.RESOURCELEFT, resourceDeck.pick());
        drawableCards.put(TablePosition.RESOURCERIGHT, resourceDeck.pick());
        drawableCards.put(TablePosition.GOLDENDECK, goldenDeck.pick());
        drawableCards.put(TablePosition.GOLDENLEFT, goldenDeck.pick());
        drawableCards.put(TablePosition.GOLDENRIGHT, goldenDeck.pick());
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
     * Return the Map of drawable cards: the four in the center + the two on top of the decks
     * @return the Map of drawable cards
     */
    public Map<TablePosition, ResourceCard> getDrawableCards() {
        return drawableCards;
    }

    /**
     * Get the game winners
     * @return the list of winners
     */
    public List<Player> getWinners() {
        List<Player> winners = new ArrayList<>();
        int maxTotalPoints = players.stream().mapToInt(Player::getTotalPoints).max().orElse(0);
        int maxObjectivePoints = players.stream().mapToInt(Player::getObjectivePoints).max().orElse(0);
        for (Player player : players) {
            if (player.getTotalPoints() == maxTotalPoints && player.getObjectivePoints() == maxObjectivePoints) {
                winners.add(player);
            }
        }
        return winners;
    }

    /**
     *
     * @param nickName the name of the player to remove
     */
    public void removePlayer(String nickName){
        players.remove(players.stream().filter(x -> x.getName().equals(nickName)).collect(Collectors.toList()).get(0));
    }
}