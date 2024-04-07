package it.polimi.ingsw.gc01.model.player;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.cards.RadixCard;
import it.polimi.ingsw.gc01.model.decks.PlayableDeck;
import it.polimi.ingsw.gc01.model.room.Room;

import java.util.Map;
import java.util.Set;

public class Player {
    private String playerName;
    private int score;
    private PlayerColor color;
    private RadixCard radixCard;
    private PlayableDeck hand;
    private Set<ObjectiveCard> objective;
    private Map<PlayerResources, Integer> resources;
    private Room room;
    private Field field;

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

    public PlayableDeck getHand() {
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

    }

    public void drawCard() {

    }
}
