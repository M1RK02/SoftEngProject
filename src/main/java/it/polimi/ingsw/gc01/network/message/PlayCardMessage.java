package it.polimi.ingsw.gc01.network.message;

public class PlayCardMessage extends ClientToServerMessage{
    private int cardId;
    private int x;
    private int y;

    public PlayCardMessage(String playerName, int cardId, int x, int y) {
        super(playerName);
        this.cardId = cardId;
        this.x = x;
        this.y = y;
    }

    public int getCardId() {
        return cardId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}