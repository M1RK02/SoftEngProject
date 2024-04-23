package it.polimi.ingsw.gc01.model.decks;

import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.strategy.*;

public class StrategyAdapter extends TypeAdapter<Strategy> {

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

    @Override
    public Strategy read(JsonReader in) throws IOException {
        Strategy strategy = null;
        in.beginObject();
        String value = in.nextName();
        switch(value) {
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