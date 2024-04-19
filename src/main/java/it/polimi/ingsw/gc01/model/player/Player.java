package it.polimi.ingsw.gc01.model.player;

import java.util.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;

import static it.polimi.ingsw.gc01.model.CornerValue.*;
import static it.polimi.ingsw.gc01.model.corners.CornerPosition.*;

public class Player {
    private final String name;
    private final PlayerColor color;
    private int points;
    private Map<PlayerResource, Integer> resources;
    private List<PlayableCard> hand;
    private Field field;
    private ObjectiveCard secretObjective;

    public Player(String name, PlayerColor color) {
        this.name = name;
        this.color = color;
        this.points = 0;
        this.resources = initResources();
        this.hand = new ArrayList<PlayableCard>();
        this.field = new Field();
    }

    private Map<PlayerResource, Integer> initResources() {
        Map<PlayerResource, Integer> resources = new HashMap<>();
        resources.put(Resource.ANIMAL, 0);
        resources.put(Resource.PLANT, 0);
        resources.put(Resource.FUNGI, 0);
        resources.put(Resource.INSECT, 0);
        resources.put(Item.QUILL, 0);
        resources.put(Item.INKWELL, 0);
        resources.put(Item.MANUSCRIPT, 0);
        return resources;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getPoints() {
        return points;
    }

    public Map<PlayerResource, Integer> getResources() {
        return resources;
    }

    public List<PlayableCard> getHand() {
        return hand;
    }

    public Field getField() {
        return field;
    }

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    public void setSecretObjective(ObjectiveCard secretObjective) {
        this.secretObjective = secretObjective;
    }

    public void addPoints(int playerPoints) {
        this.points += playerPoints;
    }

    public void addResource(PlayerResource resource) {
        resources.put(resource, resources.get(resource) + 1);
    }

    public void removeResource(PlayerResource resource) {
        resources.put(resource, resources.get(resource) - 1);
    }

    public void addCard(PlayableCard card) {
        hand.add(card);
    }

    public void playCard(PlayableCard card, Position position){
        //Rimuovere carta dalla mano
        hand.remove(card);

        //Eliminare la position dalle available position
        field.getAvailablePositions().remove(position);

        //Coprire gli angoli e decrementare le resources
        Map<CornerPosition, PlayableCard> adjacentCard = field.getAdjacentCards(position);
        coverAdjacentCorners(adjacentCard);

        Map<CornerPosition, Corner> corners = card.getCorners();
        if (card instanceof ResourceCard) {
            updatePoints((ResourceCard) card, position);
        } else if (card instanceof StarterCard){
            ceckIsFront((StarterCard) card, corners);
        }

        //Aggiunge le resources degli angoli
        addCornerResouces(card, corners);

        //Aggiungere nuove available position
        updateAvailablePosition(corners, adjacentCard, position);
        field.getPositions().put(position, card);
    }

    private void updatePoints(ResourceCard card, Position position) {
        if (card instanceof GoldenCard) {
            addPoints(((GoldenCard) card).calculatePoints(this, position));
        }else {
            addPoints(card.getScore());
        }
    }

    private void ceckIsFront(StarterCard card, Map<CornerPosition, Corner> corners) {
        if (card.isFront()) {
            for (CardResource cardResource : card.getCenterResources()) {
                addResource((PlayerResource) cardResource);
            }
        }else {
            corners = card.getBackCorners();
        }
    }

    private void addCornerResouces(PlayableCard card, Map<CornerPosition, Corner> corners) {
        for (Corner corner : corners.values()) {
            if (!corner.getResource().equals(EMPTY) && !corner.getResource().equals(CornerValue.FULL)) {
                addResource((PlayerResource) corner.getResource());
            }
        }
    }

    private void coverAdjacentCorners(Map<CornerPosition, PlayableCard> adjacentCard) {
        for (CornerPosition cornerPosition : adjacentCard.keySet()) {
            switch (cornerPosition) {
                case TOP_LEFT:
                    adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).getResource());
                    }
                    break;
                case TOP_RIGHT:
                    adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).getResource());
                    }
                    break;
                case BOTTOM_LEFT:
                    adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).getResource());
                    }
                    break;
                case BOTTOM_RIGHT:
                    adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).getResource());
                    }
                    break;
                default:
            }
        }
    }

    private void updateAvailablePosition(Map<CornerPosition, Corner> corners, Map<CornerPosition, PlayableCard> adjacentCard, Position position) {
        for (CornerPosition cornerPosition : corners.keySet()) {
            if (!adjacentCard.containsKey(cornerPosition)) {
                Position p;
                switch (cornerPosition) {
                    case TOP_LEFT:
                        p = new Position(position.getX() - 1, position.getY() + 1);
                        if (corners.get(cornerPosition).getResource().equals(FULL)) {
                            field.getUnavailablePositions().add(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.getAvailablePositions().add(p);
                            }
                        }
                        break;
                    case TOP_RIGHT:
                        p = new Position(position.getX() + 1, position.getY() + 1);
                        if (corners.get(cornerPosition).getResource().equals(FULL)) {
                            field.getUnavailablePositions().add(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.getAvailablePositions().add(p);
                            }
                        }
                        break;
                    case BOTTOM_LEFT:
                        p = new Position(position.getX() - 1, position.getY() - 1);
                        if (corners.get(cornerPosition).getResource().equals(FULL)) {
                            field.getUnavailablePositions().add(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.getAvailablePositions().add(p);
                            }
                        }
                        break;
                    case BOTTOM_RIGHT:
                        p = new Position(position.getX() + 1, position.getY() - 1);
                        if (corners.get(cornerPosition).getResource().equals(FULL)) {
                            field.getUnavailablePositions().add(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.getAvailablePositions().add(p);
                            }
                        }
                        break;
                    default:
                }
            }
        }
    }
}