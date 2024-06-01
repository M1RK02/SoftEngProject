package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.cards.ScoreCondition;
import it.polimi.ingsw.gc01.model.corners.CardResource;
import it.polimi.ingsw.gc01.model.player.PlayerResource;

public enum Item implements CardResource, PlayerResource, ScoreCondition {
    QUILL,
    INKWELL,
    MANUSCRIPT
}