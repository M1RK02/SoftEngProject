package it.polimi.ingsw.gc01.model.cards;

public abstract class Card {
    private final int id;
    private final String info;

    public Card(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }
}