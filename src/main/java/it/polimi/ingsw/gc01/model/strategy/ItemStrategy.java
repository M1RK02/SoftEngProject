package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.player.Player;

public class ItemStrategy implements Strategy {
    private final Item item;

    public ItemStrategy(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public int check(Player player) {
        if (player.getResources().get(item) % 2 == 0)
            return player.getResources().get(item);
        else
            return player.getResources().get(item) - 1;
    }
}