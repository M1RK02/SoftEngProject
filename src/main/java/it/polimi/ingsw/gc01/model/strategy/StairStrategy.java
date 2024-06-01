package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;

import java.util.*;

public class StairStrategy implements Strategy {
    private final CardColor stairColor;

    public StairStrategy(CardColor stairColor) {
        this.stairColor = stairColor;
    }

    public CardColor getStairColor() {
        return stairColor;
    }

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