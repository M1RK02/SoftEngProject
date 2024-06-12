package it.polimi.ingsw.gc01.controller.exceptions;

/**
 * Exception thrown when trying to add a player with the same name to a game
 */
public class PlayerAlreadyInException extends RuntimeException {
    /**
     * Construct a new PlayerAlreadyInException
     */
    public PlayerAlreadyInException() {
        super("MAIN Name already used in the room");
    }
}
