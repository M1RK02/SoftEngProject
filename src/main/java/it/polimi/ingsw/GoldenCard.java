package it.polimi.ingsw;
import it.polimi.ingsw.PlayableCard;

import java.util.*;

public class GoldenCard extends PlayableCard {
    private Map<Resource, Integer> requirements;
    private ScoreCondition scoreCondition;
    private Item itemCondition;
}
