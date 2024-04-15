package it.polimi.ingsw.gc01.jsonTesting;

import com.google.gson.*;
import java.lang.reflect.Type;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.corners.*;

public class CardResourcesDeserializer implements JsonDeserializer<CardResource> {
    public CardResource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return CornerValue.valueOf(json.getAsString());

        } catch (IllegalArgumentException ignored) {}

        try {
            return Item.valueOf(json.getAsString());
        } catch (IllegalArgumentException ignored) {}

        return Resource.valueOf(json.getAsString());
    }
}