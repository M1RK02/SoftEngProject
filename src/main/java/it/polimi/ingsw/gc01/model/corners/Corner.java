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

    /**
     * @return true if the corner is covered in the playing field.
     */
    public boolean isCovered(){
        return covered;
    }

    /**
     * set the covered attribute true
     */
    public void cover(){
        covered = true;
    }
}