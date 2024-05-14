package it.polimi.ingsw.gc01.network.message;

public class FieldMessage implements Message{
    private String playerName;
    private int card;
    private int x;
    private int y;

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