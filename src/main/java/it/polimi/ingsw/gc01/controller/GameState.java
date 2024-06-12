package it.polimi.ingsw.gc01.controller;

/**
 * Enumeration to indicate the state of the game
 */
public enum GameState {
    /**
     * Waiting for other players to join and set ready
     */
    WAITING,
    /**
     * Selection of the starter cards
     */
    STARTER_SELECTION,
    /**
     * Selection of the player color
     */
    COLOR_SELECTION,
    /**
     * Selection of the secret objective
     */
    OBJECTIVE_SELECTION,
    /**
     * Normal game flow
     */
    RUNNING,
    /**
     * Last turn
     */
    LAST_CIRCLE,
    /**
     * Game is ended
     */
    ENDED
}