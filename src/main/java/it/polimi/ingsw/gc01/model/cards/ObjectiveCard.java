package it.polimi.ingsw.gc01.model.cards;
import it.polimi.ingsw.gc01.model.player.Field;


import it.polimi.ingsw.gc01.model.strategy.Strategy;

public class ObjectiveCard extends Card {
    private int score;
    private Strategy strategy;

    public int getScore(Field field){
    return score;
    }

    /**
     *
     * @return the strategy to check if a field satisfies a certain objective
     */
    public Strategy getStrategy(){
        return strategy;
    }
}
