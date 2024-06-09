package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.GoldenCard;

public class GoldenDeck extends Deck {
    /**
     * Construct a GoldenDeck by calling superclass constructor with type "Golden"
     */
    public GoldenDeck() {
        super("Golden");
    }

    /**
     * Picks a card form the Golden Deck
     * @return a GoldenCard from the GoldenDeck
     */
    public GoldenCard pick() {
        return (GoldenCard) super.pick();
    }

    /**
     * Gets top card from the GoldenDeck without removing it.
     * @return a `GoldenCard` object representing the top card in the deck.
     */
    public GoldenCard get() {
        return (GoldenCard) super.get();
    }

    /**
     * ONLY FOR TESTING
     * Picks a card from the GoldenDeck by its id.
     * @param id of the card to draw from the deck by
     * @return A `GoldenCard` object with the specified ID, or `null` if no such card exists.
     */
    @Deprecated
    public GoldenCard pickById(int id) {
        return (GoldenCard) super.pickById(id);
    }
}