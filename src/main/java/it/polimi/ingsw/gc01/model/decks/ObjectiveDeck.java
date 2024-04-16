package it.polimi.ingsw.gc01.model.decks;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.*;

public class ObjectiveDeck implements Deck{
    private List<ObjectiveCard> objectiveDeck;

    public ObjectiveDeck() {
        // TODO
    }

    public ObjectiveCard pick() {
        ObjectiveCard card = objectiveDeck.get(0);
        objectiveDeck.remove(0);
        return card;
    }

    public boolean isEmpty() {
        return objectiveDeck.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(objectiveDeck, new Random(4349));
    }
}
