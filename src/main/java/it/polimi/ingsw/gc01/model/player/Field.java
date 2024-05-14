package it.polimi.ingsw.gc01.model.player;

import java.util.*;

import it.polimi.ingsw.gc01.model.CornerValue;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;

import static it.polimi.ingsw.gc01.model.CornerValue.EMPTY;

public class Field {
    private Map<Position, PlayableCard> positions;
    private Set<Position> availablePositions;
    private Set<Position> unavailablePositions;

    public Field() {
        this.positions = new HashMap<Position, PlayableCard>();
        this.availablePositions = new HashSet<Position>();
        this.availablePositions.add(new Position(0, 0));
        this.unavailablePositions = new HashSet<>();
    }

    /**
     * @return the deck's positions Map, which describes the placed card on the field.
     */
    public Map<Position, PlayableCard> getPositions() {
        return positions;
    }

    /**
     * @return the field's positions where the player is allowed to place a card.
     */
    public Set<Position> getAvailablePositions() {
        return availablePositions;
    }

    /**
     * @return the field's positions where the player is not allowed to place any card.
     */
    public Set<Position> getUnavailablePositions() {
        return unavailablePositions;
    }

    /**
     * @param position for which to return adjacent cards if they have already been placed on the field.
     * @return a Map containing the adjacent cards of the param 'position'
     */
    public Map<CornerPosition, PlayableCard> getAdjacentCards(Position position) {
        Map<CornerPosition, PlayableCard> adjacentCards = new HashMap<>();
        Position p = new Position(position.getX() - 1, position.getY() - 1);
        if (positions.containsKey(p)) {
            adjacentCards.put(CornerPosition.BOTTOM_LEFT, positions.get(p));
        }
        p = new Position(position.getX() - 1, position.getY() + 1);
        if (positions.containsKey(p)) {
            adjacentCards.put(CornerPosition.TOP_LEFT, positions.get(p));
        }
        p = new Position(position.getX() + 1, position.getY() + 1);
        if (positions.containsKey(p)) {
            adjacentCards.put(CornerPosition.TOP_RIGHT, positions.get(p));
        }
        p = new Position(position.getX() + 1, position.getY() - 1);
        if (positions.containsKey(p)) {
            adjacentCards.put(CornerPosition.BOTTOM_RIGHT, positions.get(p));
        }
        return adjacentCards;
    }
}