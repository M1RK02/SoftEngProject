package it.polimi.ingsw.gc01.model.room;

import it.polimi.ingsw.gc01.model.ChatMessage;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.decks.*;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.ObserverManager;

import java.util.*;

/**
 * Class to manage the room
 */
public class Room {
    /**
     * Id of the room
     */
    private final String roomId;
    /**
     * List of players in the room
     */
    private final List<Player> players;
    /**
     * List of available colors in the room
     */
    private final List<PlayerColor> availableColors;
    /**
     * Deck of golden cards
     */
    private final GoldenDeck goldenDeck;
    /**
     * Deck of resource cards
     */
    private final ResourceDeck resourceDeck;
    /**
     * Deck of objective cards
     */
    private final ObjectiveDeck objectiveDeck;
    /**
     * Deck of starters cards
     */
    private final StarterDeck starterDeck;
    /**
     * List of common objectives
     */
    private final List<ObjectiveCard> commonObjectives;
    /**
     * Map of drawable cards
     */
    private final Map<TablePosition, ResourceCard> drawableCards;
    /**
     * Notifier object to communicate updates
     */
    private final ObserverManager notifier;
    /**
     * Current player of the room
     */
    private Player currentPlayer;

    /**
     * Chat of the Room
     */
    private  List<ChatMessage> chat;

    /**
     * Constructs a new Room object
     *
     * @param roomId   The ID of the room.
     * @param players  The list of players in the room.
     * @param notifier The observer manager for managing observers related to this room.
     */
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
        chat = new ArrayList<>();
        initTable();
        this.notifier = notifier;
        notifier.startGame();
        notifier.updateCurrentPlayer(currentPlayer.getName());
        notifier.updateTable(drawableCards);
    }

    /**
     * Initiate the common playing field (decks and drawable cards)
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

    /**
     * @return the roomId
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * @return the list of the players in the room
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return the list of available colors to get picked
     */
    public List<PlayerColor> getAvailableColors() {
        return availableColors;
    }

    /**
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * @param currentPlayer player to be set as currentplayer
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        notifier.updateCurrentPlayer(currentPlayer.getName());
    }

    /**
     * @return the starter Deck of the game
     */
    public StarterDeck getStarterDeck() {
        return starterDeck;
    }

    /**
     * @return the resourceDeck of the game
     */
    public ResourceDeck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * @return the golden Deck of the game
     */
    public GoldenDeck getGoldenDeck() {
        return goldenDeck;
    }

    /**
     * @return the objectiveDeck of the game
     */
    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }

    /**
     * @return the commonObjectives of the game
     */
    public List<ObjectiveCard> getCommonObjectives() {
        return commonObjectives;
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
        int maxTotalPoints = players.stream().mapToInt(Player::getTotalPoints).max().orElse(0);
        List<Player> possibleWinners = players.stream().filter(player -> player.getTotalPoints() == maxTotalPoints).toList();
        int maxObjectivePoints = possibleWinners.stream().mapToInt(Player::getObjectivePoints).max().orElse(0);
        return possibleWinners.stream().filter(player -> player.getObjectivePoints() == maxObjectivePoints).toList();
    }

    /**
     * Remove a player from the room
     *
     * @param playerName of the player to remove
     */
    public void removePlayer(String playerName) {
        players.remove(getPlayerByName(playerName));
        notifier.removeObserver(playerName);
        notifier.showPlayerLeft(playerName);
    }

    /**
     * Adds the newChatMessage to the ChatMessages List
     * Notifies the Client that the chat has been updated
     * @param newChatMessage
     */
    public void newChatMessage(ChatMessage newChatMessage){
        chat.add(newChatMessage);
        notifier.updateChat(newChatMessage);

    }
}