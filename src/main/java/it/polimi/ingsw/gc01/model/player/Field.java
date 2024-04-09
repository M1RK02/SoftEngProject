package it.polimi.ingsw.gc01.model.player;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;

import java.util.*;

public class Field {
    private Player player;
    private Set<Position> availablePosition;
    private Map<Position, PlayableCard> positions;

    public Field(Player player) {
        this.player = player;
        this.positions = new HashMap<Position, PlayableCard>();
        this.availablePosition = new HashSet<Position>();
    }

    public Set<Position> getAvailablePosition() {
        return availablePosition;
    }

    public void addAvailablePosition(Position position) {
        availablePosition.add(position);
    }
}
