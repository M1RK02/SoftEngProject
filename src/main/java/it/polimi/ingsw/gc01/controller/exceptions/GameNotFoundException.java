package it.polimi.ingsw.gc01.controller.exceptions;

public class GameNotFoundException extends RuntimeException{
    public GameNotFoundException(){
        super("Game not found");
    }
}
