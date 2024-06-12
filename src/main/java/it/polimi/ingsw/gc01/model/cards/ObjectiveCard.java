package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.strategy.Strategy;

/**
 * This class is used to represent objective cards (it uses a strategy pattern)
 */
public class ObjectiveCard extends Card {
    /**
     * The strategy define the type of the objective
     */
    private final Strategy strategy;

    /**
     * Constructor of the objective card (adds strategy)
     *
     * @param id       of the card
     * @param info     for the card
     * @param strategy for the objective
     */
    public ObjectiveCard(int id, String info, Strategy strategy) {
        super(id, info);
        this.strategy = strategy;
    }

    /**
     * @param player for whom to calculate how many points he earned
     * @return the number of points got
     */
    public int calculatePoints(Player player) {
        return strategy.check(player);
    }
}