package it.polimi.ingsw.gc01.model.player;

import java.io.Serializable;
import java.util.Objects;

public class Position implements Serializable {
    private final int x;
    private final int y;

    /**
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the x coordinate of the position
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return the y coordinate of the position
     */
    public int getY() {
        return y;
    }

    /**
     * Two Position objects are considered equal if they have the same x and y coordinates.
     *
     * @param o The reference object with which to compare.
     * @return true if this Position is the same as the object argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return getX() == position.getX() && getY() == position.getY();
    }

    /**
     *
     * @return A hash code value for the position.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}