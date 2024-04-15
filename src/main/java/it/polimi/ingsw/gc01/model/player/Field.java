package it.polimi.ingsw.gc01.model.player;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.*;

public class Field {
    private Map<Position, PlayableCard> positions;
    private Set<Position> availablePositions;

    public Field() {
        this.positions = new HashMap<Position, PlayableCard>();
        this.availablePositions = new HashSet<Position>();
    }

    public Set<Position> getAvailablePositions() {
        return availablePositions;
    }

    public void addAvailablePosition(Position position) {
        availablePositions.add(position);
    }
}