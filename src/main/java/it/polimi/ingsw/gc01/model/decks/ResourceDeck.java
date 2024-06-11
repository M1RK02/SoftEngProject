package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.ResourceCard;

/**
 * Class to manage a deck of resource cards
 */
public class ResourceDeck extends Deck {
    /**
     * Construct an ResourceDeck by calling superclass constructor with type "Resource"
     */
    public ResourceDeck() {
        super("Resource");
    }

    /**
     * Picks a card from the resource Deck
     *
     * @return an 'ResourceCard' from the Deck removing it
     */
    public ResourceCard pick() {
        return (ResourceCard) super.pick();
    }

    /**
     * Gets the top card from the deck without removing it.
     *
     * @return An `ResourceCard` object representing the top card in the deck.
     */
    public ResourceCard get() {
        return (ResourceCard) super.get();
    }

    /**
     * ONLY FOR TESTING
     * Picks a card from ResourceDeck by its id.
     *
     * @param id of the card to draw from the deck by
     * @return a `ResourceCard` object with the specified ID, or `null` if no such card exists.
     */
    @Deprecated
    public ResourceCard pickById(int id) {
        return (ResourceCard) super.pickById(id);
    }
}