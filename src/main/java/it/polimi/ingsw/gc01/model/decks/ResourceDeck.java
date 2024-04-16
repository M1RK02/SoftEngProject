package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.ResourceCard;

import java.util.*;

public class ResourceDeck implements Deck{

    private List<ResourceCard> resourceDeck;

    public ResourceDeck() {
        //TODO
    }

    public ResourceCard pick() {
        ResourceCard card = resourceDeck.get(0);
        resourceDeck.remove(0);
        return card;
    }

    public ResourceCard get(){
        return resourceDeck.get(0);
    }

    public boolean isEmpty() {
        return resourceDeck.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(resourceDeck, new Random(4517));
    }
}
