package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.Card;
import it.polimi.ingsw.gc01.model.cards.StarterCard;

import java.util.*;

public class StarterDeck implements Deck{
    private List<StarterCard> starterDeck;

    public StarterDeck(List<StarterCard> starterDeck) {
        //TODO
    }

    public StarterCard pick(){
        StarterCard card = starterDeck.get(0);
        starterDeck.remove(0);
        return card;
    }

    public boolean isEmpty(){
        return starterDeck.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(starterDeck, new Random(4691));
    }
}
