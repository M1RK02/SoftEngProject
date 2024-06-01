package it.polimi.ingsw.gc01.controller.exceptions;

public class PlayerAlreadyInException extends RuntimeException {
    public PlayerAlreadyInException() {
        super("MAIN Name already used in the room");
    }
}
