package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;

/**
 * Class to manage a deck of objective cards
 */
public class ObjectiveDeck extends Deck {
    /**
     * Construct an ObjectiveDeck by calling superclass constructor with type "Golden"
     */
    public ObjectiveDeck() {
        super("Objective");
    }

    /**
     * Picks a card from the objective Deck
     *
     * @return an 'ObjectiveCard' from the Deck removing it
     */
    public ObjectiveCard pick() {
        return (ObjectiveCard) super.pick();
    }

    /**
     * Gets the top card from the deck without removing it.
     *
     * @return An `ObjectiveCard` object representing the top card in the deck.
     */
    public ObjectiveCard get() {
        return (ObjectiveCard) super.get();
    }

    /**
     * ONLY FOR TESTING
     * Picks a card from ObjectiveDeck by its id.
     *
     * @param id of the card to draw from the deck by
     * @return a `ObjectiveCard` object with the specified ID, or `null` if no such card exists.
     */
    @Deprecated
    public ObjectiveCard pickById(int id) {
        return (ObjectiveCard) super.pickById(id);
    }
}
