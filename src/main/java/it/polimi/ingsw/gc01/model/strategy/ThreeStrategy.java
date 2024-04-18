package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.player.*;

public class ThreeStrategy implements Strategy {
    public  ThreeStrategy(){
    }

    public int check(Player player){
        int quill, inkwell, manuscript;
        quill = player.getResources().get(Item.QUILL);
        inkwell = player.getResources().get(Item.INKWELL);
        manuscript = player.getResources().get(Item.MANUSCRIPT);
        return Math.min(manuscript, Math.min(quill, inkwell)) * 3;
    }
}