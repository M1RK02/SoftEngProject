package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.*;

public class GoldenDeck extends Deck {
    public GoldenDeck() {
        super("Golden");
    }

    public GoldenCard pick() {
        return (GoldenCard) super.pick();
    }

    public GoldenCard get(){
        return (GoldenCard) super.get();
    }
}