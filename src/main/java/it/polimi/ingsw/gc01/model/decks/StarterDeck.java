package it.polimi.ingsw.gc01.model.decks;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;

public class StarterDeck implements Deck{
    private List<StarterCard> starterDeck;

    public StarterDeck() {
        starterDeck = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .create();
        String fileLocation = "src/main/resources/it/polimi/ingsw/gc01/model/decks/starterDeck.json";
        List<Object> cardList = null;
        try {
            cardList = gson.fromJson(new FileReader(fileLocation), List.class);
        } catch (FileNotFoundException ignored) {}
        for (Object card : cardList) {
            starterDeck.add(gson.fromJson(card.toString(), StarterCard.class));
        }
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