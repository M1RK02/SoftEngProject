package it.polimi.ingsw.gc01.model.cards;

import java.util.*;
import it.polimi.ingsw.gc01.model.Resource;

public class BackRadixCard extends RadixCard {
    private Set<Resource> centerResources;

    public BackRadixCard(){
        // TODO
    }

    /**
     * @return the centerResources
     */
    public Set<Resource> getCenterResources(){
        return centerResources;
    }
}