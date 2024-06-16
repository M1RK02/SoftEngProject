package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.player.*;

import java.rmi.*;
import java.util.*;

/**
 * Interface for the client
 */
public interface VirtualView extends Remote {
    /**
     * Update the room id
     *
     * @param roomId of the room
     * @throws RemoteException
     */
    void updateRoomId(String roomId) throws RemoteException;

    /**
     * Show the players in the room
     *
     * @param playerNames the names of the players in the room
     * @throws RemoteException
     */
    void showPlayers(List<String> playerNames) throws RemoteException;

    /**
     * Show the player that has just joined
     *
     * @param playerName the names of the player that has just joined
     * @throws RemoteException
     */
    void showPlayerJoined(String playerName) throws RemoteException;

    /**
     * Show the player that has just left
     *
     * @param playerName the names of the player that has just left
     * @throws RemoteException
     */
    void showPlayerLeft(String playerName) throws RemoteException;

    /**
     * Show the waiting scene for every client except the one choosing
     *
     * @throws RemoteException
     */
    void showWaitingFor(String playerName, String scene) throws RemoteException;


    /**
     * Start the game
     *
     * @throws RemoteException
     */
    void startGame() throws RemoteException;

    /**
     * Update the current player
     *
     * @param playerName of the new current player
     * @throws RemoteException
     */
    void updateCurrentPlayer(String playerName) throws RemoteException;

    /**
     * Show the starter card to the player
     *
     * @param cardId of the starter card
     * @throws RemoteException
     */
    void showStarter(int cardId) throws RemoteException;

    /**
     * Show available colors to the player
     *
     * @param availableColors list of available colors
     * @throws RemoteException
     */
    void showAvailableColors(List<PlayerColor> availableColors) throws RemoteException;

    /**
     * Update readiness of a player
     *
     * @param playerName of the player to update
     * @param ready      new status of the player, true if ready, false otherwise
     * @throws RemoteException
     */
    void updateReady(String playerName, boolean ready) throws RemoteException;

    /**
     * Show common objectives
     *
     * @param objectivesIds list of common objective ids
     * @throws RemoteException
     */
    void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException;

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable card ids
     * @throws RemoteException
     */
    void showTable(Map<Integer, Integer> drawableCardsIds) throws RemoteException;

    /**
     * Show the hand
     *
     * @param cardIds list of card ids in the hand
     * @throws RemoteException
     */
    void showHand(List<Integer> cardIds) throws RemoteException;

    /**
     * Show the field of the indicated player
     *
     * @param playerName to show the field
     * @throws RemoteException
     */
    void showField(String playerName) throws RemoteException;

    /**
     * Show the points for each player
     *
     * @param points map of playerName, points
     * @throws RemoteException
     */
    void showPoints(Map<String, Integer> points, Map<PlayerColor, Integer> tablePoints) throws RemoteException;

    /**
     * Show the possible secret objectives
     *
     * @param possibleObjectivesIds list of possible objectives ids
     * @throws RemoteException
     */
    void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException;

    /**
     * Show the error message
     *
     * @param error to show
     * @throws RemoteException
     */
    void showError(String error) throws RemoteException;

    /**
     * Show the service message
     *
     * @param message to show
     * @throws RemoteException
     */
    void serviceMessage(String message) throws RemoteException;

    /**
     * Show the last turn notification
     *
     * @throws RemoteException
     */
    void showLastCircle() throws RemoteException;

    /**
     * Show the list of winners
     *
     * @param winners list of winners
     * @throws RemoteException
     */
    void showWinners(List<String> winners) throws RemoteException;

    /**
     * Update the field for the indicated player
     *
     * @param playerName         of the player to update
     * @param id                 of the newly played card
     * @param front              true if the card is played front, false otherwise
     * @param position           of the played card
     * @param availablePositions list of available positions
     */
    void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) throws RemoteException;

    /**
     * Check if the client is alive
     *
     * @throws RemoteException
     */
    void isAlive() throws RemoteException;

    /**
     * Go back to menu
     *
     * @throws RemoteException
     */
    void backToMenu() throws RemoteException;
}