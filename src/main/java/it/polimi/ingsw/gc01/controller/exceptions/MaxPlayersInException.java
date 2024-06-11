package it.polimi.ingsw.gc01.controller.exceptions;

/**
 * Exception thrown when trying to add a player to a full game
 */
public class MaxPlayersInException extends RuntimeException {
    /**
     * Construct a new MaxPlayerInException
     */
    public MaxPlayersInException() {
        super("MAIN Room full");
    }
}
