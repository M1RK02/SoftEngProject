package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.Resource;
import it.polimi.ingsw.gc01.model.corners.*;

import java.util.*;

/**
 * Class to represent the starter card, root of the player field
 */
public class StarterCard extends PlayableCard {
    /**
     * A set of the center resources in the card
     */
    private final Set<Resource> centerResources;
    /**
     * Map of corners value (different from front)
     */
    private final Map<CornerPosition, Corner> backCorners;

    /**
     * Constructor of starter card (adds center resources and back corners)
     *
     * @param id
     * @param info
     * @param corners
     * @param centerResources
     * @param backCorners
     * @see PlayableCard
     */
    public StarterCard(int id, String info, Map<CornerPosition, Corner> corners, Set<Resource> centerResources, Map<CornerPosition, Corner> backCorners) {
        super(id, info, corners);
        this.centerResources = centerResources;
        this.backCorners = backCorners;
    }

    /**
     * @return the set of resources contained at the center of the starter card.
     */
    public Set<Resource> getCenterResources() {
        return centerResources;
    }

    /**
     * @return a map containing the corners of the starterCard's back
     */
    public Map<CornerPosition, Corner> getBackCorners() {
        return backCorners;
    }
}