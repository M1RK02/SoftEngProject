package it.polimi.ingsw.gc01.model.player;

import it.polimi.ingsw.gc01.model.CornerValue;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.corners.*;
import it.polimi.ingsw.gc01.network.ObserverManager;

import java.util.*;

import static it.polimi.ingsw.gc01.model.CornerValue.*;
import static it.polimi.ingsw.gc01.model.Item.*;
import static it.polimi.ingsw.gc01.model.Resource.*;
import static it.polimi.ingsw.gc01.model.cards.CardColor.*;
import static it.polimi.ingsw.gc01.model.corners.CornerPosition.*;

/**
 * Class to manage the player
 */
public class Player {
    /**
     * Name of the player
     */
    private final String name;
    /**
     * Map of the resources possessed the player
     */
    private final Map<PlayerResource, Integer> resources;
    /**
     * Hand of the player
     */
    private final List<PlayableCard> hand;
    /**
     * Playing field of the player
     */
    private final Field field;
    /**
     * List of possible objectives
     */
    private final List<ObjectiveCard> possibleObjectives;
    /**
     * Notifier object to communicate updates
     */
    private final ObserverManager notifier;
    /**
     * Color of the player
     */
    private PlayerColor color;
    /**
     * Points obtained by the player
     */
    private int points;
    /**
     * Objective points obtained by the player
     */
    private int objectivePoints;
    /**
     * Secret objective of the player
     */
    private ObjectiveCard secretObjective;
    /**
     * State of the player, true if ready
     */
    private boolean ready;

    /**
     * Constructs a new `Player` object with the specified name and observer manager.
     *
     * @param name
     * @param notifier
     */
    public Player(String name, ObserverManager notifier) {
        this.name = name;
        this.points = 0;
        this.objectivePoints = 0;
        this.resources = initResources();
        this.hand = new ArrayList<PlayableCard>();
        this.field = new Field();
        this.possibleObjectives = new ArrayList<>();
        this.ready = false;
        this.notifier = notifier;
    }

    /**
     * @return a Map containing the initial count of all the CardResources present in the player field (empty for the moment)
     */
    private Map<PlayerResource, Integer> initResources() {
        Map<PlayerResource, Integer> resources = new HashMap<>();
        resources.put(ANIMAL, 0);
        resources.put(PLANT, 0);
        resources.put(FUNGI, 0);
        resources.put(INSECT, 0);
        resources.put(QUILL, 0);
        resources.put(INKWELL, 0);
        resources.put(MANUSCRIPT, 0);
        return resources;
    }

    /**
     * @return the Name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @return the color of the player
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * @param color set the player color to the chosen one
     */
    public void setColor(PlayerColor color) {
        this.color = color;
    }

    /**
     * @return the points of the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return the objectivePoints owned by the player
     */
    public int getObjectivePoints() {
        return objectivePoints;
    }

    /**
     * @return the sum of the points and the objectivePoints of the player (method used at the end of the game)
     */
    public int getTotalPoints() {
        return points + objectivePoints;
    }

    /**
     * @return the map of the resources in the player's field
     */
    public Map<PlayerResource, Integer> getResources() {
        return resources;
    }

    /**
     * @return the playable cards owned by the player
     */
    public List<PlayableCard> getHand() {
        return hand;
    }

    /**
     * @return the field of the player
     */
    public Field getField() {
        return field;
    }

    /**
     * @return the secretObjective chose by the player at the beginning of the game
     */
    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    /**
     * @param secretObjective The `ObjectiveCard` to be set as the player's secret objective.
     */
    public void setSecretObjective(ObjectiveCard secretObjective) {
        this.secretObjective = secretObjective;
    }

    /**
     * @return A list of `ObjectiveCard` objects representing the possible objectives for the player to choose
     */
    public List<ObjectiveCard> getPossibleObjectives() {
        return possibleObjectives;
    }

    /**
     * @return the readiness of the player
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * @return The observer manager associated with the player to update view
     */
    public ObserverManager getNotifier() {
        return notifier;
    }

    /**
     * When called, change the readiness of the player and notifies the view
     */
    public void switchReady() {
        ready = !ready;
        notifier.updateReady(name, ready);
    }

    /**
     * @param playerPoints points to add to the player
     */
    public void addPoints(int playerPoints) {
        this.points += playerPoints;
    }

    /**
     * @param objectivePoints the number of objectivePoints to add to the players's total objective points.
     */
    public void addObjectivePoints(int objectivePoints) {
        this.objectivePoints += objectivePoints;
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
     * @param card     to play on the player's field.
     * @param position where the player wants to place the card.
     */
    public void playCard(PlayableCard card, Position position) {
        Map<CornerPosition, Corner> corners = card.getCorners();

        //If card is an available card, check the side of the card
        if (card instanceof StarterCard) {
            if (card.isFront()) {
                addCenterResources((StarterCard) card);
            } else {
                corners = ((StarterCard) card).getBackCorners();
            }
        } else if (!card.isFront()) {
            //Add center resources depending on the card color
            CardColor color = ((ResourceCard) card).getColor();
            if (color.equals(RED)) {
                addResource(FUNGI);
            } else if (color.equals(BLUE)) {
                addResource(ANIMAL);
            } else if (color.equals(GREEN)) {
                addResource(PLANT);
            } else if (color.equals(PURPLE)) {
                addResource(INSECT);
            }
            //Set corners like a Map with four empty corners
            corners.replaceAll((p, v) -> new Corner(EMPTY));
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
        if (card.isFront()) {
            if (card instanceof ResourceCard) {
                updatePoints((ResourceCard) card, position);
            }
        }

        notifier.updateField(name, card.getId(), card.isFront(), position, field.getAvailablePositions().stream().toList());
    }

    /**
     * PRIVATE METHOD FOR THE METHOD PLAY
     *
     * @param card     just placed on the field for which to calculate the points the player earns.
     * @param position of the card in the player's field
     */
    private void updatePoints(ResourceCard card, Position position) {
        if (card instanceof GoldenCard) {
            addPoints(((GoldenCard) card).calculatePoints(this, position));
        } else {
            addPoints(card.getScore());
        }
    }

    /**
     * PRIVATE METHOD FOR THE METHOD PLAY
     *
     * @param card is the Starter card of the player, this method adds to the PlayerResources the CardResources present in the card
     */
    private void addCenterResources(StarterCard card) {
        for (CardResource cardResource : card.getCenterResources()) {
            addResource((PlayerResource) cardResource);
        }
    }

    /**
     * PRIVATE METHOD FOR THE METHOD PLAY
     *
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
     *
     * @param adjacentCard map of the adjacentCards to cover in the player's field
     */
    private void coverAdjacentCorners(Map<CornerPosition, PlayableCard> adjacentCard) {
        for (CornerPosition cornerPosition : adjacentCard.keySet()) {
            Map<CornerPosition, Corner> corners;
            if (adjacentCard.get(cornerPosition) instanceof StarterCard && !adjacentCard.get(cornerPosition).isFront()) {
                corners = ((StarterCard) adjacentCard.get(cornerPosition)).getBackCorners();
            } else {
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
     *
     * @param corners      of the card just played in the position
     * @param adjacentCard map of the adjacent cards of the position
     * @param position     of the just played card in the player's field
     */
    private void updateAvailablePosition(Map<CornerPosition, Corner> corners, Map<CornerPosition, PlayableCard> adjacentCard, Position position) {
        for (CornerPosition cornerPosition : corners.keySet()) {
            if (!adjacentCard.containsKey(cornerPosition)) {
                Position p;
                switch (cornerPosition) {
                    case TOP_LEFT:
                        p = new Position(position.getX() - 1, position.getY() + 1);
                        checkPositionAvailability(corners, cornerPosition, p);
                        break;
                    case TOP_RIGHT:
                        p = new Position(position.getX() + 1, position.getY() + 1);
                        checkPositionAvailability(corners, cornerPosition, p);
                        break;
                    case BOTTOM_LEFT:
                        p = new Position(position.getX() - 1, position.getY() - 1);
                        checkPositionAvailability(corners, cornerPosition, p);
                        break;
                    case BOTTOM_RIGHT:
                        p = new Position(position.getX() + 1, position.getY() - 1);
                        checkPositionAvailability(corners, cornerPosition, p);
                        break;
                    default:
                }
            }
        }
    }

    /**
     * Checks the availability of the position passed as a parameter within the player's field
     *
     * @param corners        map containing the corners of a card
     * @param cornerPosition The position of the corner being checked.
     * @param position       The position on the field corresponding to where the player wants to play the card
     */
    private void checkPositionAvailability(Map<CornerPosition, Corner> corners, CornerPosition cornerPosition, Position position) {
        if (corners.get(cornerPosition).getResource().equals(FULL)) {
            field.getUnavailablePositions().add(position);
            field.getAvailablePositions().remove(position);
        } else {
            if (!field.getUnavailablePositions().contains(position)) {
                field.getAvailablePositions().add(position);
            }
        }
    }

    /**
     * Two players are considered equal if they have the same name.
     *
     * @param o The reference object with which to compare.
     * @return true if this player is the same as the object argument
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    /**
     * @return A hash code value for the player.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}