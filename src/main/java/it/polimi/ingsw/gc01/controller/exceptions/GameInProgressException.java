package it.polimi.ingsw.gc01.controller.exceptions;

/**
 * Exception thrown when trying to add a player to a game in progress
 */
public class GameInProgressException extends RuntimeException {
    /**
     * Construct a new GameInProgressException
     */
    public GameInProgressException() {
        super("MAIN Game in progress");
    }
}
