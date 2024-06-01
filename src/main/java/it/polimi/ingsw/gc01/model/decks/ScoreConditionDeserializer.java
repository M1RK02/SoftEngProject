package it.polimi.ingsw.gc01.model.decks;

import com.google.gson.*;
import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.cards.*;

import java.lang.reflect.Type;
import java.util.stream.Stream;

public class ScoreConditionDeserializer implements JsonDeserializer<ScoreCondition> {
    public ScoreCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Stream.of(Item.values(), ConditionType.values())
                .flatMap(Stream::of)
                .filter(x -> x.name().equals(json.getAsString()))
                .findAny().orElse(null);
    }
}