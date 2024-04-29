package it.polimi.ingsw.gc01.model.decks;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import com.google.gson.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.strategy.*;

public abstract class Deck {
    private List<Card> deck;

    public Deck(String type) {
        String json = "src/main/resources/it/polimi/ingsw/gc01/model/decks/"+type+"Deck.json";
        deck = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .registerTypeAdapter(ScoreCondition.class, new ScoreConditionDeserializer())
                .registerTypeHierarchyAdapter(Strategy.class, new StrategyAdapter())
                .create();
        try {
            List<Object> cardList = gson.fromJson(new FileReader(json), List.class);
            for (Object card : cardList) {
                deck.add(gson.fromJson(card.toString(), (Type) Class.forName("it.polimi.ingsw.gc01.model.cards."+type+"Card")));
            }
        } catch (Exception ignored) {}
    }

    /**
     * @return true if the deck does not contain cards.
     */
    public boolean isEmpty() {
        return deck.isEmpty();
    }

    /**
     * shuffles the cards' deck
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * @return the card on the top of the deck after removing it
     */
    public Card pick() {
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    /**
     *ONLY FOR TESTING
     * @param id of the card to draw from the deck
     * @return the card whose id is the same of id
     */
    public Card pickById(int id) {
        Card card;
        for (Card c : deck){
            if (c.getId() == id){
                card = c;
                return card;
            }
        }
        return null;
    }

    /**
     * @return the card on the top of the deck
     */
    public Card get() {
        return deck.get(0);
    }
}