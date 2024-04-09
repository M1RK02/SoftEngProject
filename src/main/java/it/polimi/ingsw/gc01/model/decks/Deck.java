package it.polimi.ingsw.gc01.model.decks;
import it.polimi.ingsw.gc01.model.cards.Card;
import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import java.util.*;

public class Deck {

    private List<Card> deck;

    public Deck ( DeckType type) {

        switch (type) {
            case OBJECTIVE:
                /*TODO*/
            case RESOURCE:
                /*TODO*/
            case GOLDEN:
                /*TODO*/
                break;

        }
    }

    /**
     * shuffles the deck
     */
    public void shuffle(){
        Collections.shuffle(deck, new Random(4909));
    }

    public boolean isEmpty(){

        return deck.isEmpty();
    }
}

