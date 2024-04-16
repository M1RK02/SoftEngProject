package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.player.*;

public class GoldenCard extends ResourceCard{
    private final Map<Resource, Integer> requirements;
    private final ScoreCondition scoreCondition;

    public GoldenCard(int id, String info, Map<CornerPosition, Corner> corners, CardColor color, int score, Map<Resource, Integer> requirements, ScoreCondition scoreCondition) {
        super(id, info, corners, color, score);
        this.requirements = requirements;
        this.scoreCondition = scoreCondition;
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

    public int calculatePoints(Player player){
        // TODO
        return 0;
    }
}