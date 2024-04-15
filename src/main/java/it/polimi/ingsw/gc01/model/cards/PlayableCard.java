package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.player.Position;

public class PlayableCard extends Card{
    private final Map<CornerPosition, Corner> cornerMap;

    public PlayableCard(int id, String info, Map<CornerPosition, Corner> cornerMap) {
        super(id, info);
        this.cornerMap = cornerMap;
    }

    /**
     * Places the PlayableCard in the position passed by paramter into the playerfield
     */
    public void play(Position position){
        // TODO
    }
}