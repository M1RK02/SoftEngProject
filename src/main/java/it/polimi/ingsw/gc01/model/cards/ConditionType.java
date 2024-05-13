package it.polimi.ingsw.gc01.model.cards;

/**
 * List of special {@link ScoreCondition}
 * EMPTY when there is no condition
 * CORNER when the card give points for every covered corner
 */
public enum ConditionType implements ScoreCondition {
    EMPTY,
    CORNER
}