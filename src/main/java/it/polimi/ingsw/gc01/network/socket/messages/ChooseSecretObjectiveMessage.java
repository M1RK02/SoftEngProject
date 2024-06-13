package it.polimi.ingsw.gc01.network.socket.messages;

public class ChooseSecretObjectiveMessage extends ClientToServerMessage {
    private final int cardId;

    public ChooseSecretObjectiveMessage(String playerName, int cardId) {
        super(playerName);
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }
}