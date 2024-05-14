package it.polimi.ingsw.gc01.network.message;

public class FlipCardMessage extends ClientToServerMessage{
    private int cardId;

    public FlipCardMessage(String playerName, int cardId) {
        super(playerName);
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }
}