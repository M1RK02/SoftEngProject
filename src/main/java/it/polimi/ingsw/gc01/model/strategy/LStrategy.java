package it.polimi.ingsw.gc01.model.strategy;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;

public class LStrategy implements Strategy {
    private final CardColor bodyColor;

    public LStrategy(CardColor bodyColor) {
        this.bodyColor = bodyColor;
    }

    public CardColor getBodyColor() {
        return bodyColor;
    }

    public int check(Player player){
        Position origin = new Position(0,0);
        Map<Position, PlayableCard> field = player.getField().getPositions();
        CardColor leafColor = getLeafColor(bodyColor);
        Set<Position> found = new HashSet<>();
        for(Position p : field.keySet()){
            if (!found.contains(p) && !p.equals(origin)) {
                ResourceCard c = (ResourceCard) field.get(p);
                if (c.getColor().equals(leafColor)) {
                    Set <Position> body = getBodyIfRight(field, p);
                    if (body != null) {
                        found.add(p);
                        found.addAll(body);
                    }
                }
            }
        }
        return found.size();
    }

    private CardColor getLeafColor(CardColor bodyColor) {
        switch (bodyColor) {
            case RED:
                return CardColor.GREEN;
            case GREEN:
                return CardColor.PURPLE;
            case BLUE:
                return CardColor.RED;
        }
        // case PURPLE
        return CardColor.BLUE;
    }

    private Set<Position> getBodyIfRight(Map<Position, PlayableCard> field, Position p){
        Position upperBody = null, lowerBody = null;
        if (bodyColor.equals(CardColor.RED)) {
            upperBody = new Position(p.getX() - 1, p.getY() + 2);
            lowerBody = new Position(p.getX() - 1, p.getY() + 1);
        }
        if (bodyColor.equals(CardColor.GREEN)) {
            upperBody = new Position(p.getX() + 1, p.getY() + 2);
            lowerBody = new Position(p.getX() + 1, p.getY() + 1);
        }
        if (bodyColor.equals(CardColor.BLUE)) {
            upperBody = new Position(p.getX() - 1, p.getY() - 1);
            lowerBody = new Position(p.getX() - 1, p.getY() - 2);
        }
        if (bodyColor.equals(CardColor.PURPLE)) {
            upperBody = new Position(p.getX() + 1, p.getY() - 1);
            lowerBody = new Position(p.getX() + 1, p.getY() - 2);
        }
        try {
            ResourceCard upperCard = (ResourceCard) field.get(upperBody);
            ResourceCard lowerCard = (ResourceCard) field.get(lowerBody);
            if (upperCard.getColor().equals(bodyColor) && lowerCard.getColor().equals(bodyColor)) {
                return Set.of(upperBody, lowerBody);
            }
        } catch (ClassCastException ignore){}
        return null;
    }
}