package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.corners.*;

public abstract class PlayableCard extends Card{
    private Map<CornerPosition, Corner> corners;

    public PlayableCard(int id, String info, Map<CornerPosition, Corner> corners) {
        super(id, info);
        this.corners = corners;
    }

    public Map<CornerPosition, Corner> getCorners() {
        return corners;
    }
}