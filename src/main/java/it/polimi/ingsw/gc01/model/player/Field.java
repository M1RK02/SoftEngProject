package it.polimi.ingsw.gc01.model.player;

import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;

import java.util.*;

/**
 * Class to manage the playing field of a player
 */
public class Field {
    /**
     * Map of occupied positions
     */
    private final Map<Position, PlayableCard> positions;
    /**
     * Set of available positions
     */
    private final Set<Position> availablePositions;
    /**
     * Set of unavailable positions
     */
    private final Set<Position> unavailablePositions;

    /**
     * Constructs a new `Field` object, initializing the positions, available positions,
     * and unavailable positions.
     */
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
     * Get adjacent cards for the selected position
     *
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