package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.player.Player;

/**
 * Interface for the different types of strategies for the objective cards
 */
public interface Strategy {
    int check(Player player);
}