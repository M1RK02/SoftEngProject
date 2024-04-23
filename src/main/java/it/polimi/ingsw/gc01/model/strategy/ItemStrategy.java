package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.player.*;

public class ItemStrategy implements Strategy{
    private Item item;

    public ItemStrategy(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public int check(Player player){
        if (player.getResources().get(item)%2 == 0)
            return player.getResources().get(item);
        else
            return player.getResources().get(item) - 1;
    }
}