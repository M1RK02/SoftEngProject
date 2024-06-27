package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.player.Player;

/**
 * Interface for the different types of strategies for the objective cards
 */
public interface Strategy {
    /**
     * Calculates the points owned by the player from the strategy card placed
     *
     * @param player
     * @return
     */
    int check(Player player);
}