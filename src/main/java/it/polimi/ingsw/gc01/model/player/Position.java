package it.polimi.ingsw.gc01.model.player;

import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;

import java.util.Map;

public class Position {
    private Field field;
    private PlayableCard card;
    private Boolean full;

    public Position(Field field) {
        this.field = field;
        this.full = false;
    }

    public void setCard(PlayableCard card) {
        this.card = card;
    }

    public Boolean isEmpty() {
        return !full;
    }
}
