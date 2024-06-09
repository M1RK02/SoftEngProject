package it.polimi.ingsw.gc01.model.decks;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.CardColor;
import it.polimi.ingsw.gc01.model.strategy.*;

import java.io.IOException;

public class StrategyAdapter extends TypeAdapter<Strategy> {
    /**
     * Serializes a `Strategy` object into JSON format using the specified `JsonWriter`.
     * @param out The `JsonWriter` to write the JSON output to.
     * @param value the Java object to write. May be null.
     * @throws IOException
     */
    @Override
    public void write(JsonWriter out, Strategy value) throws IOException {
        out.beginObject();
        if (value instanceof ItemStrategy) {
            out.name("item");
            out.value(((ItemStrategy) value).getItem().toString());
        }
        if (value instanceof LStrategy) {
            out.name("bodyColor");
            out.value(((LStrategy) value).getBodyColor().toString());
        }
        if (value instanceof ResourceStrategy) {
            out.name("resource");
            out.value(((ResourceStrategy) value).getResource().toString());
        }
        if (value instanceof StairStrategy) {
            out.name("stairColor");
            out.value(((StairStrategy) value).getStairColor().toString());
        }
        if (value instanceof ThreeStrategy) {
            out.name("threePoint");
            out.value(true);
        }
        out.endObject();
    }

    /**
     * Deserializes a JSON object into a `Strategy` object using the specified `JsonReader`.
     *
     * @param in The `JsonReader` to read the JSON input from.
     * @return The `Strategy` object deserialized from the JSON input.
     * @throws IOException
     */
    @Override
    public Strategy read(JsonReader in) throws IOException {
        Strategy strategy = null;
        in.beginObject();
        String value = in.nextName();
        switch (value) {
            case "item":
                Item item = Item.valueOf(in.nextString());
                strategy = new ItemStrategy(item);
                break;
            case "bodyColor":
                CardColor bodyColor = CardColor.valueOf(in.nextString());
                strategy = new LStrategy(bodyColor);
                break;
            case "resource":
                Resource resource = Resource.valueOf(in.nextString());
                strategy = new ResourceStrategy(resource);
                break;
            case "stairColor":
                CardColor color = CardColor.valueOf(in.nextString());
                strategy = new StairStrategy(color);
                break;
            case "threePoint":
                in.nextBoolean();
                strategy = new ThreeStrategy();
                break;
        }
        in.endObject();
        return strategy;
    }
}