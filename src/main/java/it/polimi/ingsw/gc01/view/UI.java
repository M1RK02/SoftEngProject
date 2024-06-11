package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.player.*;

import java.util.*;

/**
 * Interface to combine different types of user interfaces
 */
public interface UI {
    /**
     * Shows the entered room
     *
     * @param roomId of the room
     */
    void showRoom(String roomId);

    /**
     * Shows an error
     *
     * @param error to show
     */
    void showError(String error);

    /**
     * Show a service message
     *
     * @param message to show
     */
    void showServiceMessage(String message);

    /**
     * Show that is the last turn
     */
    void showLastCircle();

    /**
     * Show the game winners
     *
     * @param winners list of winner names
     */
    void showWinners(List<String> winners);

    /**
     * Show the starter card
     *
     * @param cardId of the starter card
     */
    void showStarter(int cardId);

    /**
     * Show the list of available colors
     *
     * @param availableColors in the room
     */
    void showAvailableColors(List<PlayerColor> availableColors);

    /**
     * Show the current player
     *
     * @param playerName of the current player
     */
    void showCurrentPlayer(String playerName);

    /**
     * Shows the field of the indicated player
     *
     * @param playerName of the player
     */
    void showField(String playerName);

    /**
     * Show the points for each player in the room
     *
     * @param points map of playerName, points
     */
    void showPoints(Map<String, Integer> points);

    /**
     * Show the hand to the player
     *
     * @param handIds list of card ids in the hand
     */
    void showHand(List<Integer> handIds);

    /**
     * Show possible secret objective
     *
     * @param possibleObjectiveIds list of objective card ids
     */
    void showPossibleObjectives(List<Integer> possibleObjectiveIds);

    /**
     * Update the readiness of a player
     *
     * @param playerName of the player to update
     * @param ready new readiness status
     */
    void updateReady(String playerName, boolean ready);

    /**
     * Start the game
     */
    void startGame();

    /**
     * Update the field for the indicated player
     *
     * @param playerName of the player to update
     * @param id of the newly played card
     * @param front true if the card is played front, false otherwise
     * @param position of the played card
     * @param availablePositions list of available positions
     */
    void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions);

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable cards
     */
    void showTable(Map<Integer, Integer> drawableCardsIds);

    /**
     * Show the common objectives
     *
     * @param objectiveIds list of objective card ids
     */
    void showCommonObjectives(List<Integer> objectiveIds);

    /**
     * Go back to menu
     */
    void backToMenu();
}