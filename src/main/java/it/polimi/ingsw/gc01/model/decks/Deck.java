package it.polimi.ingsw.gc01.model.decks;

import java.io.*;
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
                switch(type){
                    case "Golden":
                        deck.add(gson.fromJson(card.toString(), GoldenCard.class));
                        break;
                    case "Objective":
                        deck.add(gson.fromJson(card.toString(), ObjectiveCard.class));
                        break;
                    case "Resource":
                        deck.add(gson.fromJson(card.toString(), ResourceCard.class));
                        break;
                    case "Starter":
                        deck.add(gson.fromJson(card.toString(), StarterCard.class));
                        break;
                }
            }
        } catch (Exception ignored) {}
    }

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public void shuffle() {
        Collections.shuffle(deck, new Random(4909));
    }

    public Card pick() {
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public Card get() {
        return deck.get(0);
    }
}