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
    private Set<ObjectiveCard> possibleObjective;

    public Player(String name, PlayerColor color) {
        this.name = name;
        this.color = color;
        this.points = 0;
        this.resources = initResources();
        this.hand = new ArrayList<PlayableCard>();
        this.field = new Field();
    }

    /**
     * @return a Map containing the initial count of all the CardResources present in the player field (empty for the moment)
     */
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
    public void setPossibleObjective(ObjectiveCard c){
        possibleObjective.add(c);
    }

    /**
     * @param playerPoints points to add to the player
     */
    public void addPoints(int playerPoints) {
        this.points += playerPoints;
    }

    /**
     * @param resource is the PlayerResource to increment within the map that tracks the number of CardResources visible in the player's field.
     */
    public void addResource(PlayerResource resource) {
        resources.put(resource, resources.get(resource) + 1);
    }

    /**
     * @param resource is the PlayerResource to decrement within the map that tracks the number of CardResources visible in the player's field.
     */
    public void removeResource(PlayerResource resource) {
        resources.put(resource, resources.get(resource) - 1);
    }

    /**
     * @param card to add to the player's hand
     */
    public void addCard(PlayableCard card) {
        hand.add(card);
    }

    /**
     * @param card to play on the player's field.
     * @param position where the player wants to place the card.
     */
    public void playCard(PlayableCard card, Position position){
        Map<CornerPosition, Corner> corners = card.getCorners();

        //If card is an available card, check the side of the card
        if (card instanceof StarterCard){
            if (((StarterCard) card).isFront()) {
                addCenterResources((StarterCard) card);
            }else {
                corners = ((StarterCard) card).getBackCorners();
            }
        }

        //Remove card from the hand
        hand.remove(card);

        //Remove position from available positions
        field.getAvailablePositions().remove(position);

        //Cover the adjacent corners, so decrement the resources
        coverAdjacentCorners(field.getAdjacentCards(position));

        //Add the resource of the card's corners
        addCornerResources(corners);

        //Update the available position
        updateAvailablePosition(corners, field.getAdjacentCards(position), position);

        //Update the Map<Position, PlayableCard> positions in the field
        field.getPositions().put(position, card);

        //If card is a ResourceCard, add card points to the player
        if (card instanceof ResourceCard) {
            updatePoints((ResourceCard) card, position);
        }
    }

    /**
     * PRIVATE METHOD FOR THE METHOD PLAY
     * @param card just placed on the field for which to calculate the points the player earns.
     * @param position of the card in the player's field
     */
    private void updatePoints(ResourceCard card, Position position) {
        if (card instanceof GoldenCard) {
            addPoints(((GoldenCard) card).calculatePoints(this, position));
        }else {
            addPoints(card.getScore());
        }
    }

    /**
     * PRIVATE METHOD FOR THE METHOD PLAY
     * @param card is the Starter card of the player, this method adds to the PlayerResources the CardResources present in the card
     */
    private void addCenterResources(StarterCard card) {
        for (CardResource cardResource : card.getCenterResources()) {
            addResource((PlayerResource) cardResource);
        }
    }

    /**
     * PRIVATE METHOD FOR THE METHOD PLAY
     * @param corners is a map of a card's corners that are going to update the PlayerResource count
     *                this method is called when the player has just placed a card on hi field
     */
    private void addCornerResources(Map<CornerPosition, Corner> corners) {
        for (Corner corner : corners.values()) {
            if (!corner.getResource().equals(EMPTY) && !corner.getResource().equals(CornerValue.FULL)) {
                addResource((PlayerResource) corner.getResource());
            }
        }
    }

    /**
     * PRIVATE METHOD FOR THE METHOD PLAY
     * @param adjacentCard map of the adjacentCards to cover in the player's field
     *
     */
    private void coverAdjacentCorners(Map<CornerPosition, PlayableCard> adjacentCard) {
        for (CornerPosition cornerPosition : adjacentCard.keySet()) {
            Map<CornerPosition, Corner> corners;
            if (adjacentCard.get(cornerPosition) instanceof StarterCard && !((StarterCard) adjacentCard.get(cornerPosition)).isFront()) {
                corners = ((StarterCard)adjacentCard.get(cornerPosition)).getBackCorners();
            }else{
                corners = adjacentCard.get(cornerPosition).getCorners();
            }
            switch (cornerPosition) {
                case TOP_LEFT:
                    corners.get(BOTTOM_RIGHT).cover();
                    if (corners.get(BOTTOM_RIGHT).getResource() instanceof PlayerResource) {
                        removeResource((PlayerResource) corners.get(BOTTOM_RIGHT).getResource());
                    }
                    break;
                case TOP_RIGHT:
                    corners.get(BOTTOM_LEFT).cover();
                    if (corners.get(BOTTOM_LEFT).getResource() instanceof PlayerResource) {
                        removeResource((PlayerResource) corners.get(BOTTOM_LEFT).getResource());
                    }
                    break;
                case BOTTOM_LEFT:
                    corners.get(TOP_RIGHT).cover();
                    if (corners.get(TOP_RIGHT).getResource() instanceof PlayerResource) {
                        removeResource((PlayerResource) corners.get(TOP_RIGHT).getResource());
                    }
                    break;
                case BOTTOM_RIGHT:
                    corners.get(TOP_LEFT).cover();
                    if (corners.get(TOP_LEFT).getResource() instanceof PlayerResource) {
                        removeResource((PlayerResource) corners.get(TOP_LEFT).getResource());
                    }
                    break;
                default:
            }
        }
    }

    /**
     * PRIVATE METHOD FOR THE METHOD PLAY
     * @param corners of the card just played in the position
     * @param adjacentCard map of the adjacent cards of the position
     * @param position of the just played card in the player's field
     */
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