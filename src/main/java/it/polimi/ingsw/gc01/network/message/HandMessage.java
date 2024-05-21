package it.polimi.ingsw.gc01.network.message;

import java.util.List;

public class HandMessage implements Message{
    private List<Integer> hand;

    public HandMessage(List<Integer> hand) {
        this.hand = hand;
    }

    public List<Integer> getHand() {
        return hand;
    }
}