package it.polimi.ingsw.gc01.model.player;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Field {
    private Player player;
    private Set<Position> availablePosition;
    private Position[][] positions;

    public Field(Player player) {
        this.player = player;
        this.positions = new Position[81][81];
        this.availablePosition = new HashSet<Position>();
    }

    public Set<Position> getAvailablePosition() {
        return availablePosition;
    }
}
