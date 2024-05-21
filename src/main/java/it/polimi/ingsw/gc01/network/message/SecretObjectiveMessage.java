package it.polimi.ingsw.gc01.network.message;

import java.util.List;

public class SecretObjectiveMessage implements Message{
    private List<Integer> possibleObjectives;

    public SecretObjectiveMessage(List<Integer> possibleObjectives) {
        this.possibleObjectives = possibleObjectives;
    }

    public List<Integer> getPossibleObjectives() {
        return possibleObjectives;
    }
}