package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.StarterCard;

public class StarterDeck extends Deck {

    /**
     * Construct an StarterDeck by calling superclass constructor with type "Resource"
     */
    public StarterDeck() {
        super("Starter");
    }

    /**
     * Picks a card from the Starter Deck
     *
     * @return an 'StarterCard' from the Deck removing it
     */
    public StarterCard pick() {
        return (StarterCard) super.pick();
    }

    /**
     * Gets the top card from the deck without removing it.
     *
     * @return An `StarterCard` object representing the top card in the deck.
     */
    public StarterCard get() {
        return (StarterCard) super.get();
    }

    /**
     * ONLY FOR TESTING
     * Picks a card from StarterDeck by its id.
     *
     * @param id of the card to draw from the deck by
     * @return a `StarterCard` object with the specified ID, or `null` if no such card exists.
     */
    @Deprecated
    public StarterCard pickById(int id) {
        return (StarterCard) super.pickById(id);
    }
}