package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.player.Player;

public class ThreeStrategy implements Strategy {
    public ThreeStrategy() {
    }

    public int check(Player player) {
        int quill = player.getResources().get(Item.QUILL);
        int inkwell = player.getResources().get(Item.INKWELL);
        int manuscript = player.getResources().get(Item.MANUSCRIPT);
        return Math.min(manuscript, Math.min(quill, inkwell)) * 3;
    }
}