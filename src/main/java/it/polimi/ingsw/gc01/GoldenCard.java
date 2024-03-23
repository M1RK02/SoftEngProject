package it.polimi.ingsw.gc01;

import java.util.*;

public class GoldenCard extends PlayableCard {
    private Map<Resource, Integer> requirements;
    private ScoreCondition scoreCondition;
    private Item itemCondition;
}
