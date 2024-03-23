package it.polimi.ingsw.gc01;

import java.util.*;

public class Room {
    private String roomId;
    private List<GoldenCard> goldenDrawableCard;
    private List<ResourceCard> resourceDrawableCard;
    private List<GoldenCard> goldenPile;
    private List<ResourceCard> resourcePile;
    private Set<ObjectiveCard> objectiveSet;
    private List<Player> players;
    private Player turn;
}