package it.polimi.ingsw.gc01.controller.exceptions;

public class MaxPlayerInException extends RuntimeException{
    public MaxPlayerInException(){
        super("Max number of player reached");
    }
}
