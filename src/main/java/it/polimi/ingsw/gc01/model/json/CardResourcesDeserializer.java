package it.polimi.ingsw.gc01.model.json;

import com.google.gson.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.CardResources;

import java.lang.reflect.Type;

public class CardResourcesDeserializer implements JsonDeserializer<CardResources> {
    public CardResources deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return Resource.valueOf(json.getAsString());
        } catch (IllegalArgumentException ignored) {}
        try {
            return CornerValue.valueOf(json.getAsString());
        } catch (IllegalArgumentException ignored) {}
        return Item.valueOf(json.getAsString());
    }
}