package net.nogoodshots.toyrobot;

import java.util.Objects;

/** Major component of a {@link net.nogoodshots.toyrobot.Simulation}. {@link Board} is the representation
 * of space within the {@link net.nogoodshots.toyrobot.Simulation}. {@link Board} is rectangular. Any spatial restrictions
 * can be implemented in this class.
 */
public class Board {

    private static final int DEFAULT_X_MAGNITUDE = 5;
    private static final int DEFAULT_Y_MAGNITUDE= 5;

    private final int x;
    private final int y;

    /**
     * Construct a {@link Board} with default size.
     */
    public Board() {
        this(DEFAULT_X_MAGNITUDE, DEFAULT_Y_MAGNITUDE);
    }

    /**
     * Construct a {@link Board} with specific size.
     * x = 0 represents the extreme {@link Direction#WEST}.
     * y = 0 represents the extreme {@link Direction#SOUTH}.
     * @param x Size of {@link Board} space in {@link Direction#EAST}-{@link Direction#WEST} direction.
     * @param y Size of {@link Board} space in {@link Direction#NORTH}-{@link Direction#SOUTH} direction.
     */
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

    /**
     * {@link Simulation} wishes to occupy a space on the {@link Board}.
     * @param position {@link BoardPosition} representing a position on the {@link Board}. This should not be null.
     * @return {@link BoardPosition} representing the occupied position.
     * @throws IllegalActionException thrown if the {@link Board} does not allow that position. This could be
     * because it is considered out of bounds or perhaps it is already occupied (future?).
     */
    public BoardPosition occupy(final BoardPosition position) throws IllegalActionException {
        Objects.requireNonNull(position, "Board cannot occupy a null BoardPosition");
        // Initial simulation only has occupant per board and all positions within the board are able to be occupied.
        validateBoardPosition(position);
        // Do nothing
        return position;
    }

    /**
     * {@link Simulation} wishes to occupy a position on the {@link Board} and stop occupying another.
     * @param from {@link BoardPosition} currently occupied. This should not be null.
     * @param to {@link BoardPosition} that will be occupied. This should not be null.
     * @return {@link BoardPosition} representing the occupied position.
     * @throws IllegalActionException
     */
    public BoardPosition changePosition(final BoardPosition from, final BoardPosition to) throws IllegalActionException {
        Objects.requireNonNull(from, "Board cannot accept null old position");
        Objects.requireNonNull(to, "Board cannot accept null new position");
        validateBoardPosition(to);
        // Initial simulation only has occupant per board and all positions within the board are able to be occupied.
        return to;
    }

    /**
     * Determine a new {@link BoardPosition} from a {@link BoardPosition} and {@link Direction}.
     * @param from Starting {@link BoardPosition}. This should not be null.
     * @param direction {@link Direction} in which to offset from the current position.
     * @param distance The magnitude of the offset.
     * @return New {@link BoardPosition} represeting the absolution position of the relative offset. This is not necessarily a
     * {@link BoardPosition} that can be occupied.
     */
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
