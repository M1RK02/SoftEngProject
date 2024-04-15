package it.polimi.ingsw.gc01.model.json;

import com.google.gson.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.*;

import java.util.*;

public class GsonTest {
    public static void main(String[] args) throws Exception{
        Gson gson = new GsonBuilder().registerTypeAdapter(CardResources.class, new CardResourcesDeserializer()).create();
        Map<CornerPosition, Corner> cornerMap = new HashMap<>();
        cornerMap.put(CornerPosition.BOTTOM_LEFT, new Corner(Resource.MUSHROOM));
        cornerMap.put(CornerPosition.BOTTOM_RIGHT, new Corner(CornerValue.FULL));
        cornerMap.put(CornerPosition.TOP_LEFT, new Corner(Resource.MUSHROOM));
        cornerMap.put(CornerPosition.TOP_RIGHT, new Corner(CornerValue.EMPTY));
        ResourceCard cartaProva = new ResourceCard(1, "Carta prova", cornerMap, CardColor.RED, 0);
        List<Card> deckProva = new ArrayList<>();
        deckProva.add(cartaProva);
        String json = gson.toJson(deckProva);
        System.out.println(json);

        System.out.println(cartaProva.getId());

    }
}
