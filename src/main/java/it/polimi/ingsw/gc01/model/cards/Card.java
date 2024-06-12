package it.polimi.ingsw.gc01.model.cards;

/**
 * Abstract class to manage all types of cards
 */
public abstract class Card {
    /**
     * Unique id of the card
     */
    private final int id;
    /**
     * Short description of the card (not always present)
     */
    private final String info;

    /**
     * Constructor of the card
     *
     * @param id   of the card
     * @param info for the card
     */
    public Card(int id, String info) {
        this.id = id;
        this.info = info;
    }

    /**
     * @return the card id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the card description
     */
    public String getInfo() {
        return info;
    }
}