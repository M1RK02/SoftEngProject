package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;

public class LStrategy implements Strategy {
    private CardColor bodyColor;

    public LStrategy(CardColor bodyColor) {
        this.bodyColor = bodyColor;
    }

    public CardColor getBodyColor() {
        return bodyColor;
    }

    public int check(Player player){
        // TODO
        return 0;
    }
}