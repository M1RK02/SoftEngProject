package it.polimi.ingsw.gc01.controller.exceptions;

public class CardNotValidException extends RuntimeException{
    public CardNotValidException(){
        super("Wrong card id passed to the function");
    }
}
