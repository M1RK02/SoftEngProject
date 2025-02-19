package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.corners.CardResource;
import it.polimi.ingsw.gc01.model.player.PlayerResource;

/**
 * List of game resources
 */
public enum Resource implements CardResource, PlayerResource {
    ANIMAL,
    PLANT,
    FUNGI,
    INSECT
}