package it.polimi.ingsw.gc01.model.decks;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.*;

public class Deck {
    private List<Card> deck;

    public Deck() {
        // TODO
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(deck, new Random(4909));
    }
}