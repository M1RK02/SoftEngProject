package it.polimi.ingsw.gc01.controller.exceptions;

public class NameNotValidException extends RuntimeException{
    public NameNotValidException(){
        super("The name of this player is not present in the room");
    }
}
