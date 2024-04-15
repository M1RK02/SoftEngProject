package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.Resource;
import it.polimi.ingsw.gc01.model.corners.Corner;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;

import java.util.Map;
import java.util.Set;

public class RadixCard extends PlayableCard{
    private final Set<Resource> centerResources;

    public RadixCard(int id, String info, Map<CornerPosition, Corner> cornerMap, Set<Resource> centerResources) {
        super(id, info, cornerMap);
        this.centerResources = centerResources;
    }

    public Set<Resource> getCenterResources(){
        return centerResources;
    }

}