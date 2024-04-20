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

    public int calculatePoints(Player player, Position position){
        if (scoreCondition.equals(Item.QUILL)) {
            return player.getResources().get(Item.QUILL)*getScore();
        }
        if (scoreCondition.equals(Item.INKWELL)) {
            return player.getResources().get(Item.INKWELL)*getScore();
        }
        if (scoreCondition.equals(Item.MANUSCRIPT)) {
            return player.getResources().get(Item.MANUSCRIPT)*getScore();
        }
        if (scoreCondition.equals(ConditionType.EMPTY)) {
            return getScore();
        }
        if (scoreCondition.equals(ConditionType.CORNER)) {
            int n = 0;
            for (int i = position.getX() - 1; i <= position.getX() + 1; i += 2) {
                for (int j = position.getY() - 1; j <= position.getY() + 1; j += 2) {
                    if (player.getField().getPositions().containsKey(new Position(i,j))){
                        n++;
                    }
                }
            }
            return n*getScore();
        }
        return 0;
    }
}