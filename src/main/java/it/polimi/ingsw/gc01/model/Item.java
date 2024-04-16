package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.model.player.*;

public enum Item implements CardResource, PlayerResource, ScoreCondition {
    QUILL,
    INKWELL,
    MANUSCRIPT;
}