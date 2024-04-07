package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.corners.Corner;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;
import it.polimi.ingsw.gc01.model.player.Position;

import java.util.Map;

public class PlayableCard extends Card{
    private Map<CornerPosition, Corner> cornerMap;

    /**
     * places the PlayableCard in the position passed by paramter into the playerfield
     */
    public void play(Position position){

    }
}
