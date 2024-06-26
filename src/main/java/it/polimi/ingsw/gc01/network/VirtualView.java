package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.ChatMessage;
import it.polimi.ingsw.gc01.model.player.*;

import java.io.IOException;
import java.rmi.Remote;
import java.util.*;

/**
 * Interface for the client
 */
public interface VirtualView extends Remote {
    /**
     * Update the room id
     *
     * @param roomId of the room
     * @throws IOException
     */
    void updateRoomId(String roomId) throws IOException;

    /**
     * Show the players in the room
     *
     * @param playersAlreadyIn map with key the player name and value the ready status
     * @throws IOException
     */
    void showPlayers(Map<String, Boolean> playersAlreadyIn) throws IOException;

    /**
     * Show the player that has just joined
     *
     * @param playerName the names of the player that has just joined
     * @throws IOException
     */
    void showPlayerJoined(String playerName) throws IOException;

    /**
     * Show the player that has just left
     *
     * @param playerName the names of the player that has just left
     * @throws IOException
     */
    void showPlayerLeft(String playerName) throws IOException;

    /**
     * Show the waiting scene for every client except the one choosing
     *
     * @throws IOException
     */
    void showWaitingFor(String playerName, String scene) throws IOException;


    /**
     * Start the game
     *
     * @throws IOException
     */
    void startGame() throws IOException;

    /**
     * Update the current player
     *
     * @param playerName of the new current player
     * @throws IOException
     */
    void updateCurrentPlayer(String playerName) throws IOException;

    /**
     * Show the starter card to the player
     *
     * @param cardId of the starter card
     * @throws IOException
     */
    void showStarter(int cardId) throws IOException;

    /**
     * Show available colors to the player
     *
     * @param availableColors list of available colors
     * @throws IOException
     */
    void showAvailableColors(List<PlayerColor> availableColors) throws IOException;

    /**
     * Update readiness of a player
     *
     * @param playerName of the player to update
     * @param ready      new status of the player, true if ready, false otherwise
     * @throws IOException
     */
    void updateReady(String playerName, boolean ready) throws IOException;

    /**
     * Show common objectives
     *
     * @param objectivesIds list of common objective ids
     * @throws IOException
     */
    void showCommonObjectives(List<Integer> objectivesIds) throws IOException;

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable card ids
     * @throws IOException
     */
    void showTable(Map<Integer, Integer> drawableCardsIds) throws IOException;

    /**
     * Update the drawable cards on the table
     *
     * @param drawableCardsIds the map of the ids with the positions in the table
     * @throws IOException
     */
    void updateTable(Map<Integer, Integer> drawableCardsIds) throws IOException;

    /**
     * Show the hand
     *
     * @param handIds list of card ids in the hand
     * @throws IOException
     */
    void showHand(List<Integer> handIds) throws IOException;

    /**
     * Update the hand
     *
     * @param cardIds list of card ids in the hand
     * @throws IOException
     */
    void updateHand(List<Integer> cardIds) throws IOException;

    /**
     * Show the field of the indicated player
     *
     * @param playerName to show the field
     * @throws IOException
     */
    void showField(String playerName) throws IOException;

    /**
     * Show the points for each player
     *
     * @param points map of playerName, points
     * @param colors map of color, playerName
     * @throws IOException
     */
    void showPoints(Map<String, Integer> points, Map<PlayerColor, String> colors) throws IOException;

    /**
     * Show the possible secret objectives
     *
     * @param possibleObjectivesIds list of possible objectives ids
     * @throws IOException
     */
    void showSecretObjectives(List<Integer> possibleObjectivesIds) throws IOException;

    /**
     * Show the error message
     *
     * @param error to show
     * @throws IOException
     */
    void showError(String error) throws IOException;

    /**
     * Show the service message
     *
     * @param message to show
     * @throws IOException
     */
    void serviceMessage(String message) throws IOException;

    /**
     * Show the last turn notification
     *
     * @throws IOException
     */
    void showLastCircle() throws IOException;

    /**
     * Show the list of winners
     *
     * @param winners list of winners
     * @throws IOException
     */
    void showWinners(List<String> winners) throws IOException;

    /**
     * Update the field for the indicated player
     *
     * @param playerName         of the player to update
     * @param id                 of the newly played card
     * @param front              true if the card is played front, false otherwise
     * @param position           of the played card
     * @param availablePositions list of available positions
     * @throws IOException
     */
    void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) throws IOException;

    void updateChat(ChatMessage newChatMessage) throws IOException;

    /**
     * Check if the client is alive
     *
     * @throws IOException
     */
    void isAlive() throws IOException;

    /**
     * Go back to menu
     *
     * @throws IOException
     */
    void backToMenu() throws IOException;
}