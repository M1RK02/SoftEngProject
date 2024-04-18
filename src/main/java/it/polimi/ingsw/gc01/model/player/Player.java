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

    public void playCard(ResourceCard card, Position position) {
        //Rimuovere carta dalla mano
        hand.remove(card);

        //Eliminare la position dalle available position
        field.removeAvailablePosition(position);

        //Incrementare le resources
        for (Corner corner : card.getCorners().values()) {
            if (!corner.getResource().equals(EMPTY) && !corner.getResource().equals(CornerValue.FULL)) {
                addResource((PlayerResource) corner.getResource());
            }
        }

        //Coprire gli angoli e decrementare le resources
        Map<CornerPosition, PlayableCard> adjacentCard = field.getAdjacentCards(position);
        for (CornerPosition cornerPosition : adjacentCard.keySet()) {
            switch (cornerPosition) {
                case TOP_LEFT:
                    adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).getResource());
                    };
                    break;
                case TOP_RIGHT:
                    adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).getResource());
                    };
                    break;
                case BOTTOM_LEFT:
                    adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).getResource());
                    };
                    break;
                case BOTTOM_RIGHT:
                    adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).getResource());
                    };
                    break;
                default:
            }
        }

        //Aggiungere nuove available position
        for (CornerPosition cornerPosition : card.getCorners().keySet()) {
            if (!adjacentCard.containsKey(cornerPosition)) {
                Position p;
                switch (cornerPosition) {
                    case TOP_LEFT:
                        p = new Position(position.getX() - 1, position.getY() + 1);
                        if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                            field.addUnavailablePosition(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.addAvailablePosition(p);
                            }
                        }
                        break;
                    case TOP_RIGHT:
                        p = new Position(position.getX() + 1, position.getY() + 1);
                        if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                            field.addUnavailablePosition(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.addAvailablePosition(p);
                            }
                        }
                        break;
                    case BOTTOM_LEFT:
                        p = new Position(position.getX() - 1, position.getY() - 1);
                        if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                            field.addUnavailablePosition(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.addAvailablePosition(p);
                            }
                        }
                        break;
                    case BOTTOM_RIGHT:
                        p = new Position(position.getX() + 1, position.getY() - 1);
                        if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                            field.addUnavailablePosition(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.addAvailablePosition(p);
                            }
                        }
                        break;
                    default:
                }
            }
        }

        //Incrementare i punti del player
        if (card instanceof GoldenCard) {
            points += ((GoldenCard) card).calculatePoints(this, position);
        }else {
            points += ((ResourceCard) card).getScore();
        }
    }

    public void playCard(StarterCard card, Position position) {
        //Rimuovere carta dalla mano
        hand.remove(card);

        //Eliminare la position dalle available position
        field.removeAvailablePosition(position);

        //Incrementare le resources
        for (Corner corner : card.getCorners().values()) {
            if (!corner.getResource().equals(EMPTY) && !corner.getResource().equals(CornerValue.FULL)) {
                addResource((PlayerResource) corner.getResource());
            }
            if (!card.getCenterResources().isEmpty()) {
                for (CardResource cardResource : card.getCenterResources()) {
                    addResource((PlayerResource) cardResource);
                }
            }
        }

        //Coprire gli angoli e decrementare le resources
        Map<CornerPosition, PlayableCard> adjacentCard = field.getAdjacentCards(position);
        for (CornerPosition cornerPosition : adjacentCard.keySet()) {
            switch (cornerPosition) {
                case TOP_LEFT:
                    adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_RIGHT).getResource());
                    };
                    break;
                case TOP_RIGHT:
                    adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(BOTTOM_LEFT).getResource());
                    };
                    break;
                case BOTTOM_LEFT:
                    adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(TOP_RIGHT).getResource());
                    };
                    break;
                case BOTTOM_RIGHT:
                    adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).cover();
                    if (!adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).getResource().equals(EMPTY)) {
                        removeResource((PlayerResource) adjacentCard.get(cornerPosition).getCorners().get(TOP_LEFT).getResource());
                    };
                    break;
                default:
            }
        }

        //Aggiungere nuove available position
        for (CornerPosition cornerPosition : card.getCorners().keySet()) {
            if (!adjacentCard.containsKey(cornerPosition)) {
                Position p;
                switch (cornerPosition) {
                    case TOP_LEFT:
                        p = new Position(position.getX() - 1, position.getY() + 1);
                        if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                            field.addUnavailablePosition(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.addAvailablePosition(p);
                            }
                        }
                        break;
                    case TOP_RIGHT:
                        p = new Position(position.getX() + 1, position.getY() + 1);
                        if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                            field.addUnavailablePosition(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.addAvailablePosition(p);
                            }
                        }
                        break;
                    case BOTTOM_LEFT:
                        p = new Position(position.getX() - 1, position.getY() - 1);
                        if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                            field.addUnavailablePosition(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.addAvailablePosition(p);
                            }
                        }
                        break;
                    case BOTTOM_RIGHT:
                        p = new Position(position.getX() + 1, position.getY() - 1);
                        if (card.getCorners().get(cornerPosition).getResource().equals(FULL)) {
                            field.addUnavailablePosition(p);
                        }else {
                            if (!field.getUnavailablePositions().contains(p)) {
                                field.addAvailablePosition(p);
                            }
                        }
                        break;
                    default:
                }
            }
        }
    }
}