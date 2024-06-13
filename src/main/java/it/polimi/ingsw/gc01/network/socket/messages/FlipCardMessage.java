package it.polimi.ingsw.gc01.network.socket.messages;

public class FlipCardMessage extends ClientToServerMessage {
    private final int cardId;

    public FlipCardMessage(String playerName, int cardId) {
        super(playerName);
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }
}