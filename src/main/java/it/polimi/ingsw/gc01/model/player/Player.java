package it.polimi.ingsw.gc01.model.player;

import java.util.*;
import it.polimi.ingsw.gc01.model.*;
import it.polimi.ingsw.gc01.model.cards.*;

public class Player {
    private final String name;
    private final PlayerColor color;
    private int points;
    private Map<PlayerResource, Integer> resources;
    private List<PlayableCard> hand;
    private Field field;
    private StarterCard starterCard;
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

    public StarterCard getStarterCard() {
        return starterCard;
    }

    public ObjectiveCard getSecretObjective() {
        return secretObjective;
    }

    public void addPoints(int playerPoints) {
        this.points += playerPoints;
    }

    public void addResources(PlayerResource resource, Integer n) {
        resources.put(resource, resources.get(resource) + n);
    }

    public void removeResources(PlayerResource resource, Integer n) {
        resources.put(resource, resources.get(resource) - n);
    }

    public void addCard(PlayableCard card) {
        hand.add(card);
    }

    public void playCard(PlayableCard card, Position position) {
        // TODO
    }
}