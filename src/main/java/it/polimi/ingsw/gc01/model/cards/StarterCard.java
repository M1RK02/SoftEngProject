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


    /**
     * @return true if this.front is true, false if not
     */
    public boolean isFront() {
        return front;
    }


    /**
     * @return the set of resources contained at the center of the starter card.
     */
    public Set<Resource> getCenterResources(){
        return centerResources;
    }

    /**
     * @return a map containing the corners of the starterCard's back
     */
    public Map<CornerPosition, Corner> getBackCorners(){
        return backCorners;
    }
}