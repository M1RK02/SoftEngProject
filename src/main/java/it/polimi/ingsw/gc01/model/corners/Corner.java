package it.polimi.ingsw.gc01.model.corners;

/**
 * Class to manage the corner of a card
 */
public class Corner {
    /**
     * Value of the corner
     */
    private final CardResource resource;
    /**
     * Covered status of the corner, true if it is
     */
    private boolean covered;

    /**
     * Construct a new corner object
     *
     * @param resource value of the corner
     */
    public Corner(CardResource resource) {
        this.resource = resource;
    }

    /**
     * @return the corner's resource
     */
    public CardResource getResource() {
        return resource;
    }

    /**
     * @return true if the corner is covered in the playing field.
     */
    public boolean isCovered() {
        return covered;
    }

    /**
     * Set the covered attribute true
     */
    public void cover() {
        covered = true;
    }
}