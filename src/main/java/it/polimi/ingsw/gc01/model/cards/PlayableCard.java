package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.corners.*;

public abstract class PlayableCard extends Card{
    private Map<CornerPosition, Corner> corners;
    private boolean front;

    public PlayableCard(int id, String info, Map<CornerPosition, Corner> corners) {
        super(id, info);
        this.corners = corners;
        this.front = false;
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

    public Map<CornerPosition, Corner> getCorners() {
        return corners;
    }
}