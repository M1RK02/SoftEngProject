package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.corners.*;

public class StarterCard extends PlayableCard{
    private final Set<Resource> centerResources;
    private Map<CornerPosition, Corner> backCorners;
    private boolean front;

    public StarterCard(int id, String info, Map<CornerPosition, Corner> corners, Set<Resource> centerResources, Map<CornerPosition, Corner> backCorners) {
        super(id, info, corners);
        this.centerResources = centerResources;
        this.backCorners = backCorners;
        this.front = true;
    }

    public void setFront(boolean front) {
        this.front = front;
    }

    public boolean isFront() {
        return front;
    }

    public Set<Resource> getCenterResources(){
        return centerResources;
    }

    public Map<CornerPosition, Corner> getBackCorners(){
        return backCorners;
    }
}