package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.player.Player;

/**
 * Class for the ThreeStrategy (Give three points every three different items)
 */
public class ThreeStrategy implements Strategy {
    /**
     * Constructor of the ThreeStrategy Object
     */
    public ThreeStrategy() {
    }

    /**
     * checks if the players has the correct item to get the points from the ThreeStrategy
     *
     * @param player The player whose field will be checked.
     * @return the points gained by the player due to the ThreeStrategy
     */
    public int check(Player player) {
        int quill = player.getResources().get(Item.QUILL);
        int inkwell = player.getResources().get(Item.INKWELL);
        int manuscript = player.getResources().get(Item.MANUSCRIPT);
        return Math.min(manuscript, Math.min(quill, inkwell)) * 3;
    }
}