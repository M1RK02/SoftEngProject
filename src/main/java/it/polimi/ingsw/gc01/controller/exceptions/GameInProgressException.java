package it.polimi.ingsw.gc01.controller.exceptions;

public class GameInProgressException extends RuntimeException {
    public GameInProgressException() {
        super("MAIN Game in progress");
    }
}
