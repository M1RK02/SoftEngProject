package it.polimi.ingsw.gc01.model.decks;

import java.lang.reflect.Type;
import java.util.stream.Stream;
import com.google.gson.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.corners.*;

public class CardResourcesDeserializer implements JsonDeserializer<CardResource> {
    public CardResource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Stream.of(CornerValue.values(), Item.values(), Resource.values())
                .flatMap(Stream::of)
                .filter(x -> x.name().equals(json.getAsString()))
                .findAny().orElse(null);
    }
}