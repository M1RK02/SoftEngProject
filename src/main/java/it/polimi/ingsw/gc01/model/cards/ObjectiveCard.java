package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.strategy.*;

public class ObjectiveCard extends Card {
    private final Strategy strategy;

    public ObjectiveCard (int id, String info, Strategy strategy){
        super(id, info);
        this.strategy = strategy;
    }

    /**
     * @param player for whom to calculate how many points he earned in their field relative to his objective card.
     * @return the number of points got from the ObjectiveCard calculated in the player's field
     */
    public int calculatePoints(Player player){
        return strategy.check(player);
    }
}