package it.polimi.ingsw.gc01.model.cards;

public abstract class Card {
    protected final int id;
    protected final String info;

    protected Card(int id, String info) {
        this.id = id;
        this.info = info;
    }
}