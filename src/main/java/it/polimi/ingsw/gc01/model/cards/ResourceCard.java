package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.corners.Corner;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;

import java.util.Map;

public class ResourceCard extends PlayableCard{
    private final CardColor color;
    private final int score;

    public ResourceCard(int id, String info, Map<CornerPosition, Corner> cornerMap, CardColor color, int score) {
        super(id, info, cornerMap);
        this.color = color;
        this.score = score;
    }

    /**
     * @return the score of the ResourceCard
     */
    public int getScore(){
        return score;
    }
}