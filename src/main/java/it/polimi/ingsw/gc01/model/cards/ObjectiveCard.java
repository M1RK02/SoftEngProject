package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.strategy.*;

public class ObjectiveCard extends Card {
    private final Strategy strategy;

    public ObjectiveCard (int id, String info, Strategy strategy){
        super(id, info);
        this.strategy = strategy;
    }

    public int calculatePoints(Player player){
        return strategy.check(player);
    }
}