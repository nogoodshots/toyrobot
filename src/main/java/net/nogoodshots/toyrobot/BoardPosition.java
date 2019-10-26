package net.nogoodshots.toyrobot;

import java.util.Objects;

/** A position on the {@link Board} in the {@link net.nogoodshots.toyrobot.Simulation}
 *
 */
public class BoardPosition {

    private final Integer x;
    private final Integer y;

    /**
     * Constructs a new {@link BoardPosition} in the 2D domain of the {@link Board}.
     * @param x
     * @param y
     */
    public BoardPosition(Integer x, Integer y) {
        Objects.requireNonNull(x, "BoardPosition cannot accept null x");
        Objects.requireNonNull(y, "BoardPosition cannot accept null y");
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardPosition that = (BoardPosition) o;
        return x.equals(that.x) &&
                y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "BoardPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
