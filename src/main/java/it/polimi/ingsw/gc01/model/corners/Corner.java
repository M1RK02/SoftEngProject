package it.polimi.ingsw.gc01.model.corners;

public class Corner {
    private CardResource resource;
    private boolean covered;

    public Corner (CardResource resource){
        this.resource = resource;
    }

    public CardResource getResource(){
        return resource;
    }

    public boolean isCovered(){
        return covered;
    }

    public void cover(){
        covered = true;
    }
}