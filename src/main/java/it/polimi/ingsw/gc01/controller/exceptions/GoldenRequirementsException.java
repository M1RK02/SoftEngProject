package it.polimi.ingsw.gc01.controller.exceptions;

public class GoldenRequirementsException extends RuntimeException{
    public GoldenRequirementsException(){
        super("Player don't have enough resource to play this card");
    }
}
