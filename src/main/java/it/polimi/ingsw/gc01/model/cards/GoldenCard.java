package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.corners.Corner;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.strategy.ResourceStrategy;

public class GoldenCard extends ResourceCard{
    private final Map<Resource, Integer> requirements;
    private final ScoreCondition scoreCondition;
    private final Item itemCondition;

    public GoldenCard(int id, String info, Map<CornerPosition, Corner> cornerMap, CardColor color, int score, Map<Resource, Integer> requirements, ScoreCondition scoreCondition, Item itemCondition) {
        super(id, info, cornerMap, color, score);
        this.requirements = requirements;
        this.scoreCondition = scoreCondition;
        this.itemCondition = itemCondition;
    }

    /**
     * @param player which to check the requirements
     * @return true if player can play the card else false
     */
    public boolean checkRequirements(Player player){
        for (Resource resource : requirements.keySet()) {
            if (player.getResources().get(resource) < requirements.get(resource)) {
                return false;
            }
        }
        return true;
    }
}