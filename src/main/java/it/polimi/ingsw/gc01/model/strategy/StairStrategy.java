package it.polimi.ingsw.gc01.model.strategy;

import java.util.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;

public class StairStrategy implements Strategy {
    private CardColor stairColor;

    public StairStrategy(CardColor stairColor) {
        this.stairColor = stairColor;
    }

    public CardColor getStairColor() {
        return stairColor;
    }

    public int check(Player player){
        Map<PlayableCard, Boolean> checked;
        Map<Position, PlayableCard> treasureMap = player.getField().getPositions();
        ResourceCard card, currentCard;
        int points = 0;
        int count;
        Position currentPosition;
        checked = new HashMap<>();

        for (Position p : treasureMap.keySet()){
            card = (ResourceCard) treasureMap.get(p);
            checked.put(card, false);
        }
        checked.put(treasureMap.get(new Position(0,0)), true);

        switch (stairColor){
            case RED:
                for (Position p : treasureMap.keySet()){
                    count = 0;
                    card = (ResourceCard) treasureMap.get(p);
                    currentCard = card;
                    currentPosition = p;
                    //Ogni volta che incontro una carta rossa
                    //mi sposto nella carta rossa più in basso
                    //e più a sinistra presente collegata a questa
                    while (currentCard.getColor().equals(CardColor.RED) && !checked.get(currentCard) && treasureMap.containsKey(currentPosition)){
                        card = currentCard;
                        currentPosition = new Position(p.getX()-1,p.getY()-1);
                        if (treasureMap.containsKey(currentPosition)){
                            currentCard = (ResourceCard) treasureMap.get(currentPosition);
                        }
                    }

                    while (card.getColor().equals(CardColor.RED) && !checked.get(card)){
                        count += 1;
                        if (count % 3 == 0){
                            points += 2;
                            checked.put(card, true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX()-1, currentPosition.getY()-1)), true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX()-2, currentPosition.getY()-2)), true);
                        }
                        currentPosition = new Position(p.getX() + 1, p.getY() + 1);
                        card = (ResourceCard) treasureMap.get(currentPosition);
                    }
                }
                break;
            case BLUE:
                for (Position p : treasureMap.keySet()){
                    count = 0;
                    card = (ResourceCard) treasureMap.get(p);
                    currentCard = card;
                    currentPosition = p;
                    while (currentCard.getColor().equals(CardColor.BLUE) && !checked.get(currentCard) && treasureMap.containsKey(currentPosition)){
                        card = currentCard;
                        currentPosition = new Position(p.getX()-1,p.getY()-1);
                        if (treasureMap.containsKey(currentPosition)){
                            currentCard = (ResourceCard) treasureMap.get(currentPosition);
                        }
                    }

                    while (card.getColor().equals(CardColor.BLUE) && !checked.get(card)){
                        count += 1;
                        if (count % 3 == 0){
                            points += 2;
                            checked.put(card, true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX()-1, currentPosition.getY()-1)), true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX()-2, currentPosition.getY()-2)), true);
                        }
                        currentPosition = new Position(p.getX() + 1, p.getY() + 1);
                        card = (ResourceCard) treasureMap.get(currentPosition);
                    }
                }
                break;
            case GREEN:
                for (Position p : treasureMap.keySet()){
                    count = 0;
                    card = (ResourceCard) treasureMap.get(p);
                    currentCard = card;
                    currentPosition = p;
                    while (currentCard.getColor().equals(CardColor.GREEN) && !checked.get(currentCard) && treasureMap.containsKey(currentPosition)){
                        card = currentCard;
                        currentPosition = new Position(p.getX()+1,p.getY()-1);
                        if (treasureMap.containsKey(currentPosition)){
                            currentCard = (ResourceCard) treasureMap.get(currentPosition);
                        }
                    }

                    while (card.getColor().equals(CardColor.GREEN) && !checked.get(card)){
                        count += 1;
                        if (count % 3 == 0){
                            points += 2;
                            checked.put(card, true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX()+1, currentPosition.getY()-1)), true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX()+2, currentPosition.getY()-2)), true);
                        }
                        currentPosition = new Position(p.getX() - 1, p.getY() + 1);
                        card = (ResourceCard) treasureMap.get(currentPosition);
                    }
                }
                break;
            case PURPLE:
                for (Position p : treasureMap.keySet()){
                    count = 0;
                    card = (ResourceCard) treasureMap.get(p);
                    currentCard = card;
                    currentPosition = p;
                    while (currentCard.getColor().equals(CardColor.PURPLE) && !checked.get(currentCard) && treasureMap.containsKey(currentPosition)){
                        card = currentCard;
                        currentPosition = new Position(p.getX()+1,p.getY()-1);
                        if (treasureMap.containsKey(currentPosition)){
                            currentCard = (ResourceCard) treasureMap.get(currentPosition);
                        }
                    }

                    while (card.getColor().equals(CardColor.PURPLE) && !checked.get(card)){
                        count += 1;
                        if (count % 3 == 0){
                            points += 2;
                            checked.put(card, true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX()+1, currentPosition.getY()-1)), true);
                            checked.put(treasureMap.get(new Position(currentPosition.getX()+2, currentPosition.getY()-2)), true);
                        }
                        currentPosition = new Position(p.getX() - 1, p.getY() + 1);
                        card = (ResourceCard) treasureMap.get(currentPosition);
                    }
                }
                break;
            default:
        }
        return points;
    }
}