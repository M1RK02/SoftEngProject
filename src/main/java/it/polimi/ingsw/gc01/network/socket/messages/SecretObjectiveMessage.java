package it.polimi.ingsw.gc01.network.socket.messages;

import java.util.List;

public class SecretObjectiveMessage implements Message {
    private final List<Integer> possibleObjectives;

    public SecretObjectiveMessage(List<Integer> possibleObjectives) {
        this.possibleObjectives = possibleObjectives;
    }

    public List<Integer> getPossibleObjectives() {
        return possibleObjectives;
    }
}