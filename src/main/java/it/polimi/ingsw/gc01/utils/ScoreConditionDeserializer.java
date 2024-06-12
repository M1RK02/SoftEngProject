package it.polimi.ingsw.gc01.utils;

import com.google.gson.*;
import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.cards.*;

import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * Class used by Gson to deserialize the `ScoreCondition` interface
 */
public class ScoreConditionDeserializer implements JsonDeserializer<ScoreCondition> {
    /**
     * Deserializes the provided JSON data into a `ScoreCondition` object.
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context The context for deserialization, providing any additional
     *                information needed for the deserialization process.
     * @return The `ScoreCondition` object corresponding to the JSON string, or `null`
     * if no matching value is found.
     * @throws JsonParseException
     */
    public ScoreCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Stream.of(Item.values(), ConditionType.values())
                .flatMap(Stream::of)
                .filter(x -> x.name().equals(json.getAsString()))
                .findAny().orElse(null);
    }
}