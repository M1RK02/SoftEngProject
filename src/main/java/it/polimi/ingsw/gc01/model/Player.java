package it.polimi.ingsw.gc01.model;

import java.util.*;

public class Player {
    private String playerName;
    private int score;
    private Color color;
    private Boolean turn;
    private Set<PlayableCard> deck;
    private Set<ObjectiveCard> objectiveDeck;
    private Map<PlayerResources, Integer> resources;
    private Room room;
}
