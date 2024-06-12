package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.corners.*;

import java.util.Map;

/**
 * Class to represent Resource Card
 */
public class ResourceCard extends PlayableCard {
    /**
     * Color of the card
     */
    private final CardColor color;
    /**
     * Points that the card gives to the player when played (optional)
     */
    private final int score;

    /**
     * Constructor of the resource card (adds color and score)
     *
     * @param id      of the card
     * @param info    for the card
     * @param corners map of corners
     * @param color   of the card
     * @param score   of the card
     */
    public ResourceCard(int id, String info, Map<CornerPosition, Corner> corners, CardColor color, int score) {
        super(id, info, corners);
        this.color = color;
        this.score = score;
    }

    /**
     * @return the color of the card
     */
    public CardColor getColor() {
        return color;
    }

    /**
     * @return the score of the card
     */
    public int getScore() {
        return score;
    }
}