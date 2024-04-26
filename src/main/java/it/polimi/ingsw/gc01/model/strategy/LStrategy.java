package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;

import java.util.*;

public class LStrategy implements Strategy {
    private final CardColor bodyColor;
    private final CardColor leafColor;

    public LStrategy(CardColor bodyColor) {
        this.bodyColor = bodyColor;
        this.leafColor = getCorrespondingLeafColor(bodyColor);
    }

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

    public CardColor getBodyColor() {
        return bodyColor;
    }

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


    private List<Position> getBodyIfRight(Map<Position, PlayableCard> field, Position p) {
        Position upperBody = null, lowerBody = null;
        switch (bodyColor) {
            case GREEN:
                upperBody = new Position(p.getX() + 1, p.getY() + 3);
                lowerBody = new Position(p.getX() + 1, p.getY() + 1);
                break;
            case BLUE:
                upperBody = new Position(p.getX() - 1, p.getY() - 1);
                lowerBody = new Position(p.getX() - 1, p.getY() - 3);
                break;
            case RED:
                upperBody = new Position(p.getX() - 1, p.getY() + 3);
                lowerBody = new Position(p.getX() - 1, p.getY() + 1);
                break;
            case PURPLE:
                upperBody = new Position(p.getX() + 1, p.getY() - 1);
                lowerBody = new Position(p.getX() + 1, p.getY() - 3);
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