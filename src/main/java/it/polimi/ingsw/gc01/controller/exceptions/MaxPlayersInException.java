package it.polimi.ingsw.gc01.controller.exceptions;

public class MaxPlayersInException extends RuntimeException{
    public MaxPlayersInException(){
        super("MAIN Room full");
    }
}
