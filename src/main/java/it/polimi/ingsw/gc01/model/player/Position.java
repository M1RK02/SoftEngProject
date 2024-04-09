package it.polimi.ingsw.gc01.model.player;

public class Position {
    private Field field;
    private int x;
    private int y;

    public Position(Field field, int x, int y) {
        this.field = field;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
