package it.polimi.ingsw.gc01.network.message;

import java.util.List;

public class CommonObjectiveMessage implements Message{
    private List<Integer> cards;

    public CommonObjectiveMessage(List<Integer> cards) {
        this.cards = cards;
    }

    public List<Integer> getCards() {
        return cards;
    }
}