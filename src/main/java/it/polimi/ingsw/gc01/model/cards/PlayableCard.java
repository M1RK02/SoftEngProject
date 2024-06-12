package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.corners.*;

import java.util.Map;

/**
 * Abstract class to merge {@link StarterCard}, {@link ResourceCard} as they are both playable by the player
 */
public abstract class PlayableCard extends Card {
    /**
     * Map of what the corners contain
     */
    private final Map<CornerPosition, Corner> corners;
    /**
     * True if the card is showing the front else false
     */
    private boolean front;

    /**
     * Constructor of a playable card (adds cordners and front)
     *
     * @param id      of the card
     * @param info    for the card
     * @param corners map of corners
     */
    public PlayableCard(int id, String info, Map<CornerPosition, Corner> corners) {
        super(id, info);
        this.corners = corners;
        this.front = false;
    }

    /**
     * @return the map of corner values
     */
    public Map<CornerPosition, Corner> getCorners() {
        return corners;
    }

    /**
     * @return true if the card is showing the front else false
     */
    public boolean isFront() {
        return front;
    }

    /**
     * Set the front of the card
     *
     * @param front
     */
    public void setFront(boolean front) {
        this.front = front;
    }
}