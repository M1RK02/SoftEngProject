package it.polimi.ingsw.gc01.network.message;

public class ChooseSecretObjectiveMessage extends ClientToServerMessage {
    private int cardId;

    public ChooseSecretObjectiveMessage(String playerName, int cardId) {
        super(playerName);
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }
}