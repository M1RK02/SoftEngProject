package it.polimi.ingsw.gc01.model.strategy;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;

public class StairStrategy implements Strategy {
    private final CardColor stairColor;

    public StairStrategy(CardColor stairColor) {
        this.stairColor = stairColor;
    }

    public CardColor getStairColor() {
        return stairColor;
    }


    public int check(Player player){
        Map<PlayableCard, Boolean> checked = new HashMap<>();
        Map<Position, PlayableCard> treasureMap = new HashMap<>(player.getField().getPositions());
        treasureMap.remove(new Position(0,0));
        ResourceCard card, currentCard;
        Position currentPosition;
        int points = 0, count;

        for (Position p : treasureMap.keySet()){
            card = (ResourceCard) treasureMap.get(p);
            checked.put(card, false);
        }

        switch (stairColor) {
            case RED:
                for (Position p : treasureMap.keySet()) {
                    count = 0;
                    card = (ResourceCard) treasureMap.get(p);
                    currentCard = card;
                    currentPosition = p;
                    while (treasureMap.containsKey(currentPosition) && currentCard.getColor().equals(CardColor.RED) && !checked.get(currentCard)) {
                        card = currentCard;
                        currentPosition = new Position(currentPosition.getX() - 1, currentPosition.getY() - 1);
                        if (currentPosition.equals(new Position(0,0))){
                            break;
                        }
                        currentCard = (ResourceCard) treasureMap.get(currentPosition);
                    }

                    currentPosition = new Position(currentPosition.getX() + 1, currentPosition.getY() + 1);

                    while (treasureMap.containsKey(currentPosition) && card.getColor().equals(CardColor.RED) && !checked.get(card)) {
                        count++;
                        if (count % 3 == 0) {
                            points += 2;
                            checked.put(card, true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX() - 1, currentPosition.getY() - 1)), true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX() - 2, currentPosition.getY() - 2)), true);
                        }
                        currentPosition = new Position(currentPosition.getX() + 1, currentPosition.getY() + 1);
                        if (currentPosition.equals(new Position(0,0))){
                            break;
                        }
                        card = (ResourceCard) treasureMap.get(currentPosition);
                    }
                }
                break;
            case BLUE:
                for (Position p : treasureMap.keySet()) {
                    count = 0;
                    card = (ResourceCard) treasureMap.get(p);
                    currentCard = card;
                    currentPosition = p;
                    while (treasureMap.containsKey(currentPosition) && currentCard.getColor().equals(CardColor.BLUE) && !checked.get(currentCard)) {
                        card = currentCard;
                        currentPosition = new Position(currentPosition.getX() - 1, currentPosition.getY() - 1);
                        if (currentPosition.equals(new Position(0,0))){
                            break;
                        }
                        currentCard = (ResourceCard) treasureMap.get(currentPosition);
                    }

                    currentPosition = new Position(currentPosition.getX() + 1, currentPosition.getY() + 1);

                    while (treasureMap.containsKey(currentPosition) && card.getColor().equals(CardColor.BLUE) && !checked.get(card)) {
                        count++;
                        if (count % 3 == 0) {
                            points += 2;
                            checked.put(card, true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX() - 1, currentPosition.getY() - 1)), true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX() - 2, currentPosition.getY() - 2)), true);
                        }
                        currentPosition = new Position(currentPosition.getX() + 1, currentPosition.getY() + 1);
                        if (currentPosition.equals(new Position(0,0))){
                            break;
                        }
                        card = (ResourceCard) treasureMap.get(currentPosition);
                    }
                }
                break;
            case GREEN:
                for (Position p : treasureMap.keySet()) {
                    count = 0;
                    card = (ResourceCard) treasureMap.get(p);
                    currentCard = card;
                    currentPosition = p;
                    while (treasureMap.containsKey(currentPosition) && currentCard.getColor().equals(CardColor.GREEN) && !checked.get(currentCard)) {
                        card = currentCard;
                        currentPosition = new Position(currentPosition.getX() + 1, currentPosition.getY() - 1);
                        if (currentPosition.equals(new Position(0,0))){
                            break;
                        }
                        currentCard = (ResourceCard) treasureMap.get(currentPosition);
                    }

                    currentPosition = new Position(currentPosition.getX() - 1, currentPosition.getY() + 1);

                    while (treasureMap.containsKey(currentPosition) && card.getColor().equals(CardColor.GREEN) && !checked.get(card)) {
                        count++;
                        if (count % 3 == 0) {
                            points += 2;
                            checked.put(card, true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX() + 1, currentPosition.getY() - 1)), true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX() + 2, currentPosition.getY() - 2)), true);
                        }
                        currentPosition = new Position(currentPosition.getX() - 1, currentPosition.getY() + 1);
                        if (currentPosition.equals(new Position(0,0))){
                            break;
                        }
                        card = (ResourceCard) treasureMap.get(currentPosition);
                    }
                }
                break;
            case PURPLE:
                for (Position p : treasureMap.keySet()) {
                    count = 0;
                    card = (ResourceCard) treasureMap.get(p);
                    currentCard = card;
                    currentPosition = p;
                    while (treasureMap.containsKey(currentPosition) && currentCard.getColor().equals(CardColor.PURPLE) && !checked.get(currentCard)) {
                        card = currentCard;
                        currentPosition = new Position(currentPosition.getX() + 1, currentPosition.getY() - 1);
                        if (currentPosition.equals(new Position(0,0))){
                            break;
                        }
                        currentCard = (ResourceCard) treasureMap.get(currentPosition);
                    }

                    currentPosition = new Position(currentPosition.getX() - 1, currentPosition.getY() + 1);

                    while (treasureMap.containsKey(currentPosition) && card.getColor().equals(CardColor.PURPLE) && !checked.get(card)) {
                        count++;
                        if (count % 3 == 0) {
                            points += 2;
                            checked.put(card, true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX() + 1, currentPosition.getY() - 1)), true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX() + 2, currentPosition.getY() - 2)), true);
                        }
                        currentPosition = new Position(currentPosition.getX() - 1, currentPosition.getY() + 1);
                        if (currentPosition.equals(new Position(0,0))){
                            break;
                        }
                        card = (ResourceCard) treasureMap.get(currentPosition);
                    }
                }
                break;
        }
        return points;
    }

}