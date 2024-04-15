package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.corners.Corner;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;

public class ResourceCard extends PlayableCard{
    private final CardColor color;
    private final int score;

    public ResourceCard(int id, String info, Map<CornerPosition, Corner> corners, CardColor color, int score) {
        super(id, info, corners);
        this.color = color;
        this.score = score;
    }

    public CardColor getColor() {
        return color;
    }

    public int getScore(){
        return score;
    }
}