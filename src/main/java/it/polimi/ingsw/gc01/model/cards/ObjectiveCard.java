package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.player.Field;
import it.polimi.ingsw.gc01.model.strategy.Strategy;

public class ObjectiveCard extends Card {
    private final int score;
    private final Strategy strategy;

    public ObjectiveCard (int score, Strategy strategy){
        this.score = score;
        this.strategy = strategy;
    }

    public int getScore(Field field){
        return score;
    }

    /**
     * @return the strategy to check if a field satisfies a certain objective
     */
    public Strategy getStrategy(){
        return strategy;
    }
}