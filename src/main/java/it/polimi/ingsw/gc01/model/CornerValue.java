package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.corners.CardResource;

/**
 * List of special corner values
 */
public enum CornerValue implements CardResource {
    /**
     * When there is no resources
     */
    EMPTY,
    /**
     * When there is no corner
     */
    FULL
}