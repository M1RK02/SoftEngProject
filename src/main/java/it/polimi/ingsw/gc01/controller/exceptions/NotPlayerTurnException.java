package it.polimi.ingsw.gc01.controller.exceptions;

public class NotPlayerTurnException extends RuntimeException{
    public NotPlayerTurnException(){
        super("Not your turn");
    }
}
