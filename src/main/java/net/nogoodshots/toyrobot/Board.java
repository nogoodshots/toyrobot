package net.nogoodshots.toyrobot;

import net.nogoodshots.toyrobot.actions.impl.IllegalCommandException;

import java.util.Objects;

public class Board {

    private static final int DEFAULT_X_MAGNITUDE = 5;
    private static final int DEFAULT_Y_MAGNITUDE= 5;

    private final int x;
    private final int y;

    public Board() {
        this(DEFAULT_X_MAGNITUDE, DEFAULT_Y_MAGNITUDE);
    }

    public Board(final int x, final int y) {
        if (x < 1) {
            throw new IllegalArgumentException("Board x dimension must be >= 1");
        }
        if (y < 1) {
            throw new IllegalArgumentException("Board y dimension must be >= 1");
        }
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BoardPosition occupy(final BoardPosition position) throws IllegalActionException {
        Objects.requireNonNull(position, "Board cannot occupy a null BoardPosition");
        // Initial simulation only has occupant per board and all positions within the board are able to be occupied.
        validateBoardPosition(position);
        // Do nothing
        return position;
    }

    public BoardPosition changePosition(final BoardPosition from, final BoardPosition to) throws IllegalActionException {
        Objects.requireNonNull(from, "Board cannot accept null old position");
        Objects.requireNonNull(to, "Board cannot accept null new position");
        validateBoardPosition(to);
        // Initial simulation only has occupant per board and all positions within the board are able to be occupied.
        return to;
    }

    public BoardPosition getRelativePosition(final BoardPosition from, final Direction direction, final int distance) {
        Objects.requireNonNull(from, "Board getRelativePosition cannot accept null position");
        Objects.requireNonNull(direction, "Board getRelativePosition cannot accept null direction");
        final BoardPosition result;
        switch (direction) {
            case NORTH:
                result = new BoardPosition(from.getX(), from.getY() + distance);
                break;
            case SOUTH:
                result = new BoardPosition(from.getX(), from.getY() - distance);
                break;
            case EAST:
                result = new BoardPosition(from.getX() + distance, from.getY());
                break;
            case WEST:
            default:
                result = new BoardPosition(from.getX() - distance, from.getY());
                break;
        }
        return result;
    }

    private void validateBoardPosition(final BoardPosition position) throws IllegalActionException {
        // Board origin is bottom left corner, <0 is invalid
        if ((position.getX() >= x) || (position.getX() < 0) || (position.getY() >= y) || (position.getY() < 0)) {
            throw new IllegalActionException("BoardPosition is outside bounds of Board: " + position.toString());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return x == board.x &&
                y == board.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
