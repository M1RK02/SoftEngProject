package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.GoldenCard;

import java.util.*;

public class GoldenDeck implements Deck {
    private List<GoldenCard> goldenDeck;

    public GoldenDeck() {
        //TODO
    }

    public GoldenCard pick() {
        GoldenCard card = goldenDeck.get(0);
        goldenDeck.remove(0);
        return card;
    }

    public GoldenCard get(){
        return goldenDeck.get(0);
    }

    public boolean isEmpty() {
        return goldenDeck.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(goldenDeck, new Random(4909));
    }
}
