package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.player.Player;

public class GoldenCard extends ResourceCard{
    private Map<Resource, Integer> requirements;
    private ScoreCondition scoreCondition;
    private Item itemCondition;

    public GoldenCard(){
        // TODO
    }

    /**
     * @param player
     * @return checks if the field of the player in possession of the card is allowed to play it
     */
    public boolean checkRequirements(Player player){
        // TODO
        return false;
    }
}