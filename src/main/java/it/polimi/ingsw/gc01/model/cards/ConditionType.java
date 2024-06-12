package it.polimi.ingsw.gc01.model.cards;

/**
 * List of special ScoreCondition
 */
public enum ConditionType implements ScoreCondition {
    /**
     * When there is no condition
     */
    EMPTY,
    /**
     * When the card give points for every covered corner
     */
    CORNER
}