package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.*;

public class ObjectiveDeck extends Deck{
    public ObjectiveDeck() {
        super("Objective");
    }

    public ObjectiveCard pick() {
        return (ObjectiveCard) super.pick();
    }

    public ObjectiveCard get() {
        return (ObjectiveCard) super.get();
    }
}