package it.polimi.ingsw.gc01.model.room;

import it.polimi.ingsw.gc01.model.ObserverManager;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.decks.*;
import it.polimi.ingsw.gc01.model.player.*;

import java.util.*;

public class Room {
    private final String roomId;
    private final List<Player> players;
    private final List<PlayerColor> availableColors;
    private final GoldenDeck goldenDeck;
    private final ResourceDeck resourceDeck;
    private final ObjectiveDeck objectiveDeck;
    private final StarterDeck starterDeck;
    private final List<ObjectiveCard> commonObjectives;
    private final Map<TablePosition, ResourceCard> drawableCards;
    private final ObserverManager notifier;
    private Player currentPlayer;

    public Room(String roomId, List<Player> players, ObserverManager notifier) {
        this.roomId = roomId;
        this.players = players;
        availableColors = new ArrayList<>(Arrays.asList(PlayerColor.values()));
        currentPlayer = players.getFirst();
        goldenDeck = new GoldenDeck();
        resourceDeck = new ResourceDeck();
        objectiveDeck = new ObjectiveDeck();
        starterDeck = new StarterDeck();
        commonObjectives = new ArrayList<>();
        drawableCards = new HashMap<>();
        initTable();
        this.notifier = notifier;
        notifier.startGame();
        notifier.updateCurrentPlayer(currentPlayer.getName());
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
        drawableCards.get(TablePosition.RESOURCELEFT).setFront(true);
        drawableCards.put(TablePosition.RESOURCERIGHT, resourceDeck.pick());
        drawableCards.get(TablePosition.RESOURCERIGHT).setFront(true);
        drawableCards.put(TablePosition.GOLDENDECK, goldenDeck.pick());
        drawableCards.put(TablePosition.GOLDENLEFT, goldenDeck.pick());
        drawableCards.get(TablePosition.GOLDENLEFT).setFront(true);
        drawableCards.put(TablePosition.GOLDENRIGHT, goldenDeck.pick());
        drawableCards.get(TablePosition.GOLDENRIGHT).setFront(true);
        commonObjectives.add(objectiveDeck.pick());
        commonObjectives.add(objectiveDeck.pick());
    }

    public String getRoomId() {
        return roomId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<PlayerColor> getAvailableColors() {
        return availableColors;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        notifier.updateCurrentPlayer(currentPlayer.getName());
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

    public ObserverManager getNotifier() {
        return notifier;
    }

    public Player getPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Return the next player
     *
     * @return the next player
     */
    public Player getNextPlayer() {
        int currentPlayerIndex = players.indexOf(getCurrentPlayer());
        return currentPlayerIndex == players.size() - 1 ? players.get(0) : players.get(currentPlayerIndex + 1);
    }

    /**
     * Return the Map of drawable cards: the four in the center + the two on top of the decks
     *
     * @return the Map of drawable cards
     */
    public Map<TablePosition, ResourceCard> getDrawableCards() {
        return drawableCards;
    }

    /**
     * Get the game winners
     *
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
     * Remove a player from the room
     *
     * @param player the player to remove
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }
}