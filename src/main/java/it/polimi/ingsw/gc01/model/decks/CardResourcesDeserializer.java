package it.polimi.ingsw.gc01.model.decks;

import com.google.gson.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.corners.CardResource;

import java.lang.reflect.Type;
import java.util.stream.Stream;

public class CardResourcesDeserializer implements JsonDeserializer<CardResource> {
    /**
     *
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context The context for deserialization
     * @return The `CardResource` object corresponding to the JSON string, or `null`
     *  *      if no matching value is found.
     * @throws JsonParseException If the JSON is not a valid representation for a `CardResource`.
     */
    @Override
    public CardResource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Stream.of(CornerValue.values(), Item.values(), Resource.values())
                .flatMap(Stream::of)
                .filter(x -> x.name().equals(json.getAsString()))
                .findAny().orElse(null);
    }
}