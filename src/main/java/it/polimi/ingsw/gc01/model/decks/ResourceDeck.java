package it.polimi.ingsw.gc01.model.decks;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;

public class ResourceDeck implements Deck{

    private List<ResourceCard> resourceDeck;

    public ResourceDeck(){
        resourceDeck = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .create();
        String fileLocation = "src/main/resources/it/polimi/ingsw/gc01/model/decks/resourceDeck.json";
        List<Object> cardList = null;
        try {
            cardList = gson.fromJson(new FileReader(fileLocation), List.class);
        } catch (FileNotFoundException ignored) {}
        for (Object card : cardList) {
            resourceDeck.add(gson.fromJson(card.toString(), ResourceCard.class));
        }
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