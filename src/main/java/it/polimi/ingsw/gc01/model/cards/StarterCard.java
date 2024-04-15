package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.corners.*;

public class StarterCard extends PlayableCard{
    private final Set<Resource> centerResources;

    public StarterCard(int id, String info, Map<CornerPosition, Corner> corners, Set<Resource> centerResources) {
        super(id, info, corners);
        this.centerResources = centerResources;
    }

    public Set<Resource> getCenterResources(){
        return centerResources;
    }
}