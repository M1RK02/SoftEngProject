package it.polimi.ingsw.gc01.network.socket.messages;

public class FieldMessage implements Message {
    private final String playerName;
    private final int card;
    private final int x;
    private final int y;

    public FieldMessage(String playerName, int card, int x, int y) {
        this.playerName = playerName;
        this.card = card;
        this.x = x;
        this.y = y;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getCard() {
        return card;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}