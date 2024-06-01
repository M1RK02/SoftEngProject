package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.StarterCard;

public class StarterDeck extends Deck {
    public StarterDeck() {
        super("Starter");
    }

    public StarterCard pick() {
        return (StarterCard) super.pick();
    }

    public StarterCard get() {
        return (StarterCard) super.get();
    }

    @Deprecated
    public StarterCard pickById(int id) {
        return (StarterCard) super.pickById(id);
    }
}