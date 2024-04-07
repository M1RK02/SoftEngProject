package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.Resource;
import it.polimi.ingsw.gc01.model.player.Player;

import java.util.Map;

public class GoldenCard extends ResourceCard{

    private Map<Resource, Integer> requirements;

    private ScoreCondition scoreCondition;

    private Item itemCondition;


    /**
     *
     * @return the number of points of the golden card, if the points are related
     * to the position of the card in the field throws an exception.
     */
    public int getScore(){
    return 0;
    }

    /**
     *
     * @param player
     * @return checks if the field of the player in possession of the card is allowed
     * to play it
     */
    public boolean checkRequirements(Player player){
    return false;
    }
}
