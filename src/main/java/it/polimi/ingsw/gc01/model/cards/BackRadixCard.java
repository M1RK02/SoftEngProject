package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.Resource;

import java.util.Set;

public class BackRadixCard extends RadixCard {

    private Set<Resource> centerResources;

    /**
     *
     * @return the centerResources
     */
    public Set<Resource> getCenterResources(){
        return centerResources;
    }
}
