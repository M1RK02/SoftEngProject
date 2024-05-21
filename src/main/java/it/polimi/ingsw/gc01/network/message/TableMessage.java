package it.polimi.ingsw.gc01.network.message;

import it.polimi.ingsw.gc01.model.room.TablePosition;

import java.util.Map;

public class TableMessage implements Message{
    private Map<TablePosition, Integer> drawableCards;

    public TableMessage(Map<TablePosition, Integer> drawableCards) {
        this.drawableCards = drawableCards;
    }

    public Map<TablePosition, Integer> getDrawableCards() {
        return drawableCards;
    }
}