package it.polimi.ingsw.gc01.model.decks;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;

public class GoldenDeck implements Deck {
    private List<GoldenCard> goldenDeck;

    public GoldenDeck() {
        goldenDeck = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .registerTypeAdapter(ScoreCondition.class, new ScoreConditionDeserializer())
                .create();
        String fileLocation = "src/main/resources/it/polimi/ingsw/gc01/model/decks/goldenDeck.json";
        List<Object> cardList = null;
        try {
            cardList = gson.fromJson(new FileReader(fileLocation), List.class);
        } catch (FileNotFoundException ignored) {}
        for (Object card : cardList) {
            goldenDeck.add(gson.fromJson(card.toString(), GoldenCard.class));
        }
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
