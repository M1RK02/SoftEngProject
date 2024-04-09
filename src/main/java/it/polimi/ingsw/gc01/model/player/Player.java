package it.polimi.ingsw.gc01.model.player;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.cards.RadixCard;
import it.polimi.ingsw.gc01.model.room.Room;

import java.util.*;

import static it.polimi.ingsw.gc01.model.Item.*;
import static it.polimi.ingsw.gc01.model.Resource.*;

public class Player {
    private String playerName;
    private int score;
    private PlayerColor color;
    private RadixCard radixCard;
    private List<PlayableCard> hand;
    private Set<ObjectiveCard> objective;
    private Map<PlayerResources, Integer> resources;
    private Room room;
    private Field field;

    public Player(Room room, String playerName, PlayerColor color) {
        this.room = room;
        this.playerName = playerName;
        this.color = color;
        this.hand = new ArrayList<PlayableCard>();
        this.objective = new HashSet<ObjectiveCard>();
        Map<PlayerResources, Integer> resources = new HashMap<PlayerResources, Integer>();
        resources.put(WOLF, 0);
        resources.put(LEAF, 0);
        resources.put(BUTTERFLY, 0);
        resources.put(MUSHROOM, 0);
        resources.put(QUILL, 0);
        resources.put(INKWELL, 0);
        resources.put(MANUSCRIPT, 0);
        this.resources = resources;
        this.field = new Field(this);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public PlayerColor getColor() {
        return color;
    }

    public RadixCard getRadixCard() {
        return radixCard;
    }

    public List<PlayableCard> getHand() {
        return hand;
    }

    public Set<ObjectiveCard> getObjective() {
        return objective;
    }

    public Map<PlayerResources, Integer> getResources() {
        return resources;
    }

    public Room getRoom() {
        return room;
    }

    public Field getField() {
        return field;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addResource(PlayerResources resource, Integer n) {
        resources.put(resource, resources.get(resource) + 1);
    }

    public void removeResource(PlayerResources resource) {
        resources.put(resource, resources.get(resource) - 1);
    }

    public void drawCard(PlayableCard card) {
        hand.add(card);
    }
}
