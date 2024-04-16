package it.polimi.ingsw.gc01.jsonTesting;

import java.io.*;
import java.util.*;
import com.google.gson.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.player.Player;

public class GsonTesting {

    public static void main(String[] args) throws Exception{
        insideTestGold();
        fileTest();
    }

    private static void fileTest() throws Exception{
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .create();

        String fileLocation = "src/main/java/it/polimi/ingsw/gc01/jsonTesting/resourceDeck.json";
        List<Object> cardList = gson.fromJson(new FileReader(fileLocation), List.class);

        List<Card> deck = new ArrayList<>();
        for (Object card : cardList) {
            deck.add(gson.fromJson(card.toString(), ResourceCard.class));
        }
    }

    private static void insideTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .create();
        ResourceCard sampleCard = new ResourceCard(
                1,
                "SampleCard",
                generateSampleCornerMap(),
                CardColor.RED,
                0);

        List<Card> sampleCards = new ArrayList<>();
        sampleCards.add(sampleCard);

        String json = gson.toJson(sampleCards);
        System.out.println(json);

        List<Object> sampleCardsCopy = gson.fromJson(json, List.class);
        ResourceCard sampleCardCopy = gson.fromJson(sampleCardsCopy.get(0).toString(), ResourceCard.class);
    }

    private static void insideTestGold() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CardResource.class, new CardResourcesDeserializer())
                .create();
        GoldenCard sampleCard = new GoldenCard(
                1,
                "SampleCard",
                generateSampleCornerMap(),
                CardColor.RED,
                0,
                generateSampleRequirementsMap(),
                ConditionType.CORNER
                );

        List<Card> sampleCards = new ArrayList<>();
        sampleCards.add(sampleCard);

        String json = gson.toJson(sampleCards);
        System.out.println(json);

        List<Object> sampleCardsCopy = gson.fromJson(json, List.class);
        GoldenCard sampleCardCopy = gson.fromJson(sampleCardsCopy.get(0).toString(), GoldenCard.class);
    }

    private static Map<CornerPosition, Corner> generateSampleCornerMap(){
        Map<CornerPosition, Corner> cornerMap = new HashMap<>();
        cornerMap.put(CornerPosition.BOTTOM_LEFT, new Corner(Resource.FUNGI));
        cornerMap.put(CornerPosition.BOTTOM_RIGHT, new Corner(CornerValue.FULL));
        cornerMap.put(CornerPosition.TOP_LEFT, new Corner(Resource.FUNGI));
        cornerMap.put(CornerPosition.TOP_RIGHT, new Corner(CornerValue.EMPTY));
        return cornerMap;
    }

    private static Map<Resource, Integer> generateSampleRequirementsMap(){
        Map<Resource, Integer> requirementsMap = new HashMap<>();
        requirementsMap.put(Resource.FUNGI, 3);
        requirementsMap.put(Resource.PLANT, 1);
        requirementsMap.put(Resource.INSECT, 2);
        requirementsMap.put(Resource.ANIMAL, 0);
        return requirementsMap;
    }
}
