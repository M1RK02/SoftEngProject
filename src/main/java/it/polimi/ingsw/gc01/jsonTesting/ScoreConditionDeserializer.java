package it.polimi.ingsw.gc01.jsonTesting;

import java.lang.reflect.Type;
import java.util.stream.Stream;
import com.google.gson.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.*;

public class ScoreConditionDeserializer implements JsonDeserializer<ScoreCondition> {
    public ScoreCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Stream.of(Item.values(), ConditionType.values())
                .flatMap(Stream::of)
                .filter(x -> x.name().equals(json.getAsString()))
                .findAny().orElse(null);
    }
}