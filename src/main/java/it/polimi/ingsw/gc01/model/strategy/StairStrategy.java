package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;

import java.util.*;

/**
 * Class for the StairStrategy (Give three points for each successful pattern)
 */
public class StairStrategy implements Strategy {
    /**
     * Color of the stair pattern
     */
    private final CardColor stairColor;

    /**
     * Constructor of the stair strategy Object
     *
     * @param stairColor color of the cards making the stair shape in the field
     */
    public StairStrategy(CardColor stairColor) {
        this.stairColor = stairColor;
    }

    /**
     * @return the color of the cards making the stair shape in the field
     */
    public CardColor getStairColor() {
        return stairColor;
    }

    /**
     * Checks the number of cards forming a chain structure on the player's field
     *
     * @param player The player whose field will be checked.
     * @return points gained by the player performing the stair strategy in his field
     */
    public int check(Player player) {
        Position origin = new Position(0, 0);
        Map<Position, PlayableCard> field = player.getField().getPositions();
        Set<Position> found = new HashSet<>();
        for (Position p : field.keySet()) {
            if (!found.contains(p) && !p.equals(origin)) {
                ResourceCard c = (ResourceCard) field.get(p);
                if (c.getColor().equals(stairColor)) {
                    Position currentPos = getLowerCard(field, p);
                    Set<Position> chain = getChainIfRight(field, currentPos);
                    if (chain != null) {
                        found.addAll(chain);
                    }
                }
            }
        }
        return (found.size() / 3) * 2;
    }

    /**
     * Retrieves the position of the lower card in a stair structure starting from the specified position.
     *
     * @param field    The field to search for the lower card.
     * @param position The position of the upper card in the stair structure.
     * @return The position of the lower card in the stair structure.
     */
    private Position getLowerCard(Map<Position, PlayableCard> field, Position position) {
        Position origin = new Position(0, 0);
        ResourceCard card = (ResourceCard) field.get(position);
        Position currentPosition = new Position(position.getX(), position.getY()), nextPosition;
        if (stairColor.equals(CardColor.RED)) {
            nextPosition = new Position(position.getX() - 1, position.getY() - 1);
            while (!nextPosition.equals(origin) && field.containsKey(nextPosition) && card.getColor().equals(CardColor.RED)) {
                card = (ResourceCard) field.get(nextPosition);
                if (card.getColor().equals(CardColor.RED)) {
                    currentPosition = nextPosition;
                }
                nextPosition = new Position(nextPosition.getX() - 1, nextPosition.getY() - 1);
            }
        }
        if (stairColor.equals(CardColor.BLUE)) {
            nextPosition = new Position(position.getX() - 1, position.getY() - 1);
            while (!nextPosition.equals(origin) && field.containsKey(nextPosition) && card.getColor().equals(CardColor.BLUE)) {
                card = (ResourceCard) field.get(nextPosition);
                if (card.getColor().equals(CardColor.BLUE)) {
                    currentPosition = nextPosition;
                }
                nextPosition = new Position(nextPosition.getX() - 1, nextPosition.getY() - 1);
            }
        }
        if (stairColor.equals(CardColor.GREEN)) {
            nextPosition = new Position(position.getX() + 1, position.getY() - 1);
            while (!nextPosition.equals(origin) && field.containsKey(nextPosition) && card.getColor().equals(CardColor.GREEN)) {
                card = (ResourceCard) field.get(nextPosition);
                if (card.getColor().equals(CardColor.GREEN)) {
                    currentPosition = nextPosition;
                }
                nextPosition = new Position(nextPosition.getX() + 1, nextPosition.getY() - 1);
            }
        }
        if (stairColor.equals(CardColor.PURPLE)) {
            nextPosition = new Position(position.getX() + 1, position.getY() - 1);
            while (!nextPosition.equals(origin) && field.containsKey(nextPosition) && card.getColor().equals(CardColor.PURPLE)) {
                card = (ResourceCard) field.get(nextPosition);
                if (card.getColor().equals(CardColor.PURPLE)) {
                    currentPosition = nextPosition;
                }
                nextPosition = new Position(nextPosition.getX() + 1, nextPosition.getY() - 1);
            }
        }


        return currentPosition;
    }

    /**
     * Retrieves the positions forming a chain structure starting from the specified position.
     *
     * @param field    The field to search for the chain structure.
     * @param position The position of the upper card in the stair structure.
     * @return A set containing the positions forming the chain structure.
     */
    private Set<Position> getChainIfRight(Map<Position, PlayableCard> field, Position position) {
        Position origin = new Position(0, 0);
        ResourceCard card = (ResourceCard) field.get(position);
        Position currentPosition = new Position(position.getX(), position.getY()), nextPosition;
        Set<Position> found = null;
        int count = 1;
        if (stairColor.equals(CardColor.RED)) {
            nextPosition = new Position(currentPosition.getX() + 1, currentPosition.getY() + 1);
            found = new HashSet<>();
            while (!nextPosition.equals(origin) && field.containsKey(nextPosition) && card.getColor().equals(CardColor.RED)) {
                card = (ResourceCard) field.get(nextPosition);
                if (card.getColor().equals(CardColor.RED)) {
                    currentPosition = nextPosition;
                    count++;
                    if (count % 3 == 0) {
                        found.add(currentPosition);
                        found.add(new Position(currentPosition.getX() - 1, currentPosition.getY() - 1));
                        found.add(new Position(currentPosition.getX() - 2, currentPosition.getY() - 2));
                    }
                }
                nextPosition = new Position(nextPosition.getX() + 1, nextPosition.getY() + 1);
            }
        }
        if (stairColor.equals(CardColor.BLUE)) {
            nextPosition = new Position(currentPosition.getX() + 1, currentPosition.getY() + 1);
            found = new HashSet<>();
            while (!nextPosition.equals(origin) && field.containsKey(nextPosition) && card.getColor().equals(CardColor.BLUE)) {
                card = (ResourceCard) field.get(nextPosition);
                if (card.getColor().equals(CardColor.BLUE)) {
                    currentPosition = nextPosition;
                    count++;
                    if (count % 3 == 0) {
                        found.add(currentPosition);
                        found.add(new Position(currentPosition.getX() - 1, currentPosition.getY() - 1));
                        found.add(new Position(currentPosition.getX() - 2, currentPosition.getY() - 2));
                    }
                }
                nextPosition = new Position(nextPosition.getX() + 1, nextPosition.getY() + 1);
            }
        }
        if (stairColor.equals(CardColor.GREEN)) {
            nextPosition = new Position(currentPosition.getX() - 1, currentPosition.getY() + 1);
            found = new HashSet<>();
            while (!nextPosition.equals(origin) && field.containsKey(nextPosition) && card.getColor().equals(CardColor.GREEN)) {
                card = (ResourceCard) field.get(nextPosition);
                if (card.getColor().equals(CardColor.GREEN)) {
                    currentPosition = nextPosition;
                    count++;
                    if (count % 3 == 0) {
                        found.add(currentPosition);
                        found.add(new Position(currentPosition.getX() + 1, currentPosition.getY() - 1));
                        found.add(new Position(currentPosition.getX() + 2, currentPosition.getY() - 2));
                    }
                }
                nextPosition = new Position(nextPosition.getX() - 1, nextPosition.getY() + 1);
            }
        }
        if (stairColor.equals(CardColor.PURPLE)) {
            nextPosition = new Position(currentPosition.getX() - 1, currentPosition.getY() + 1);
            found = new HashSet<>();
            while (!nextPosition.equals(origin) && field.containsKey(nextPosition) && card.getColor().equals(CardColor.PURPLE)) {
                card = (ResourceCard) field.get(nextPosition);
                if (card.getColor().equals(CardColor.PURPLE)) {
                    currentPosition = nextPosition;
                    count++;
                    if (count % 3 == 0) {
                        found.add(currentPosition);
                        found.add(new Position(currentPosition.getX() + 1, currentPosition.getY() - 1));
                        found.add(new Position(currentPosition.getX() + 2, currentPosition.getY() - 2));
                    }
                }
                nextPosition = new Position(nextPosition.getX() - 1, nextPosition.getY() + 1);
            }
        }

        return found;
    }
}