package it.polimi.ingsw.gc01.model.decks;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.*;

public class Deck {
    private List<Card> deck;

    public Deck(DeckType type) {
        switch (type) {
            case OBJECTIVE:
                // TODO
            case RESOURCE:
                // TODO
            case GOLDEN:
                // TODO
        }
    }

    /**
     * Shuffles the deck
     */
    public void shuffle() {
        Collections.shuffle(deck, new Random(4909));
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }
}