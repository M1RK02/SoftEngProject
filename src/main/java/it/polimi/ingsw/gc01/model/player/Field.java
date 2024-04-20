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

    public Map<Position, PlayableCard> getPositions() {
        return positions;
    }

    public Set<Position> getAvailablePositions() {
        return availablePositions;
    }

    public Set<Position> getUnavailablePositions() {
        return unavailablePositions;
    }

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

    public void put(Position p, PlayableCard card, Player player){
        positions.put(p, card);
        availablePositions.remove(p);
        Map<CornerPosition, Corner> corners = card.getCorners();
        for (Corner corner : corners.values()) {
            if (!corner.getResource().equals(EMPTY) && !corner.getResource().equals(CornerValue.FULL)) {
                player.addResource((PlayerResource) corner.getResource());
            }
        }

        for (int i = p.getX() - 1; i <= p.getX() + 1; i += 2) {
            for (int j = p.getY() - 1; j <= p.getY() + 1; j += 2) {
                if (!positions.containsKey(new Position(i,j))){
                    availablePositions.add(new Position(i,j));
                }
            }
        }

    }
}