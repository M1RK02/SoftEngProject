package it.polimi.ingsw.gc01.controller.exceptions;

public class ColorAlreadyTakenException extends RuntimeException{
    public ColorAlreadyTakenException(){
        super("Color already taken");
    }
}
