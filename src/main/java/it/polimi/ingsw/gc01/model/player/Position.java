package it.polimi.ingsw.gc01.model.player;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        assert o instanceof Position;
        Position p = (Position) o;
        return x == p.getX() && y == p.getY();
    }
}