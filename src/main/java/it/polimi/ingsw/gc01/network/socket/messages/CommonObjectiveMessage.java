package it.polimi.ingsw.gc01.network.socket.messages;

import java.util.List;

public class CommonObjectiveMessage implements Message {
    private final List<Integer> cards;

    public CommonObjectiveMessage(List<Integer> cards) {
        this.cards = cards;
    }

    public List<Integer> getCards() {
        return cards;
    }
}