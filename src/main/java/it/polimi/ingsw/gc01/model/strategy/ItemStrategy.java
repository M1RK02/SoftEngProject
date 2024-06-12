package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.player.Player;

/**
 * Class for the item strategy (Give two points for each pair of item)
 */
public class ItemStrategy implements Strategy {
    /**
     * Item of the strategy
     */
    private final Item item;

    /**
     * Constructor of the ItemStrategy Objcet
     *
     * @param item The item associated with this strategy card
     */
    public ItemStrategy(Item item) {
        this.item = item;
    }

    /**
     * @return the item associated with the strategy card
     */
    public Item getItem() {
        return item;
    }

    /**
     * Checks if the player achieved the Itemstrategy
     *
     * @param player the player of which to check if the field achieved the ItemStrategy
     * @return The number of times the player's field achieved the ItemStrategy
     */
    public int check(Player player) {
        if (player.getResources().get(item) % 2 == 0)
            return player.getResources().get(item);
        else
            return player.getResources().get(item) - 1;
    }
}