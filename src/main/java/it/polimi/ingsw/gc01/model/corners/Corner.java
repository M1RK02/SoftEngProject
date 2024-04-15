package it.polimi.ingsw.gc01.model.corners;

import it.polimi.ingsw.gc01.model.cards.CardResources;

public class Corner {
    private CardResources object;
    private boolean covered;

    public Corner (CardResources object){
        this.object = object;
    }

    /**
     * @return the corner's object
     */
    public CardResources getObject(){
        return object;
    }

    /**
     * @return true or false depending on whether covered or not
     */
    public boolean isCovered(){
        return covered;
    }

    public void cover(){
        covered = true;
    }
}