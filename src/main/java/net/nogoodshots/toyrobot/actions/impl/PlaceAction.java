package net.nogoodshots.toyrobot.actions.impl;

import net.nogoodshots.toyrobot.Direction;
import net.nogoodshots.toyrobot.RobotAction;

import java.util.Objects;

public class PlaceAction implements RobotAction {

    private final Integer x;
    private final Integer y;
    private final Direction direction;

    public PlaceAction(final Integer x, final Integer y, final Direction direction) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(direction);

        Objects.requireNonNull(x);
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceAction that = (PlaceAction) o;
        return x.equals(that.x) &&
                y.equals(that.y) &&
                direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }

    @Override
    public String toString() {
        return "PlaceAction{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                '}';
    }
}
