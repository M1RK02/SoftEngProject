package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;

import java.util.*;

public class LStrategy implements Strategy {
    private final CardColor bodyColor;
    private final CardColor leafColor;

    /**
     * Constructor of the LStrategy Object
     *
     * @param bodyColor
     */
    public LStrategy(CardColor bodyColor) {
        this.bodyColor = bodyColor;
        this.leafColor = getCorrespondingLeafColor(bodyColor);
    }

    /**
     * @param bodyColor the body color of the strategyCard
     * @return the leaf color corresponding to the body color
     */
    private CardColor getCorrespondingLeafColor(CardColor bodyColor) {
        switch (bodyColor) {
            case GREEN:
                return CardColor.PURPLE;
            case BLUE:
                return CardColor.RED;
            case RED:
                return CardColor.GREEN;
            default: //PURPLE
                return CardColor.BLUE;
        }
    }

    /**
     * @return the color of the cards being alligned to achieve the LStrategy
     */
    public CardColor getBodyColor() {
        return bodyColor;
    }

    /**
     * Checks the number of cards forming a L structure on the player's field.
     *
     * @param player The player whose field will be checked.
     * @return The number of times the player's field achieved the LStrategy
     */
    public int check(Player player) {
        Map<Position, PlayableCard> field = player.getField().getPositions();
        List<Position> leafCandidates = getLeafCandidates(field);
        Set<Position> found = new HashSet<>();
        for (Position leaf : leafCandidates) {
            List<Position> body = getBodyIfRight(field, leaf);
            if (body != null && !found.contains(body.get(0)) && !found.contains(body.get(1))) {
                found.add(leaf);
                found.addAll(body);
            }
        }
        return found.size();
    }

    /**
     * Retrieves the leaf candidates from the field.
     * A leaf candidate is a position on the field that contains a ResourceCard
     * with a color matching the specified leaf color.
     *
     * @param field The field to search for leaf candidates.
     * @return A list of positions representing the leaf candidates.
     */
    private List<Position> getLeafCandidates(Map<Position, PlayableCard> field) {
        Position origin = new Position(0, 0);
        List<Position> candidates = new ArrayList<>();
        for (Position p : field.keySet()) {
            if (!p.equals(origin)) {
                ResourceCard card = (ResourceCard) field.get(p);
                if (card.getColor() == leafColor) {
                    candidates.add(p);
                }
            }
        }
        candidates.sort(Comparator.comparing(Position::getY));
        return candidates;
    }


    /**
     * Retrieves the positions of the upper and lower body cards if they exist and have the specified body color.
     *
     * @param field    The field to search for body cards.
     * @param position The position of the leaf card to check for body cards.
     * @return A list containing the positions of the upper and lower body cards if they exist and have the specified color,
     * or null if either of the body cards does not exist or has a different color.
     */
    private List<Position> getBodyIfRight(Map<Position, PlayableCard> field, Position position) {
        Position upperBody = null, lowerBody = null;
        switch (bodyColor) {
            case GREEN:
                upperBody = new Position(position.getX() + 1, position.getY() + 3);
                lowerBody = new Position(position.getX() + 1, position.getY() + 1);
                break;
            case BLUE:
                upperBody = new Position(position.getX() - 1, position.getY() - 1);
                lowerBody = new Position(position.getX() - 1, position.getY() - 3);
                break;
            case RED:
                upperBody = new Position(position.getX() - 1, position.getY() + 3);
                lowerBody = new Position(position.getX() - 1, position.getY() + 1);
                break;
            case PURPLE:
                upperBody = new Position(position.getX() + 1, position.getY() - 1);
                lowerBody = new Position(position.getX() + 1, position.getY() - 3);
                break;
        }
        Position origin = new Position(0, 0);
        if (field.containsKey(upperBody) && field.containsKey(lowerBody)) {
            if (!upperBody.equals(origin) && !lowerBody.equals(origin)) {
                ResourceCard upperCard = (ResourceCard) field.get(upperBody);
                ResourceCard lowerCard = (ResourceCard) field.get(lowerBody);
                if (upperCard.getColor().equals(bodyColor) && lowerCard.getColor().equals(bodyColor)) {
                    return List.of(upperBody, lowerBody);
                }
            }
        }
        return null;
    }
}