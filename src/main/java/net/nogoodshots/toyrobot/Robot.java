package net.nogoodshots.toyrobot;

import net.nogoodshots.toyrobot.actions.impl.LeftTurnAction;
import net.nogoodshots.toyrobot.actions.impl.MoveAction;
import net.nogoodshots.toyrobot.actions.impl.PlaceAction;
import net.nogoodshots.toyrobot.actions.impl.ReportAction;
import net.nogoodshots.toyrobot.actions.impl.RightTurnAction;

import java.util.Objects;
import java.util.Optional;

/**
 * Main inhabitant of {@link Simulation}.
 */
public class Robot {

    private final Board board;
    private BoardPosition boardPosition;
    private Direction facingDirection;

    /** Construct a new {@link Robot}.
     *
     * @param board The space which the {@link Robot} can occupy. This should not be null.
     */
    public Robot(final Board board) {
        Objects.requireNonNull(board, "Robot board cannot be null");
        this.board = board;
        this.boardPosition = null;
        this.facingDirection = null;
    }

    public Board getBoard() {
        return board;
    }

    public Optional<BoardPosition> getBoardPosition() {
        return Optional.ofNullable(boardPosition);
    }

    public Optional<Direction> getFacingDirection() {
        return Optional.ofNullable(facingDirection);
    }

    /** The {@link Robot} should interact with the {@link net.nogoodshots.toyrobot.Simulation}.
     *
     * @param action The legal {@link RobotAction} to be attempted. This should not be null.
     * @return The {@link Outcome} of attempting the {@link RobotAction}
     */
    public Outcome performAction(final RobotAction action) {
        Objects.requireNonNull(action, "Robot performAction cannot accept null action");
        try {
            if (action instanceof PlaceAction) {
                final PlaceAction placeAction = (PlaceAction) action;
                return performPlaceAction(placeAction);
            }
            if (action instanceof LeftTurnAction) {
                final LeftTurnAction leftTurnAction = (LeftTurnAction) action;
                return performLeftTurnAction(leftTurnAction);
            }
            if (action instanceof RightTurnAction) {
                final RightTurnAction rightTurnAction = (RightTurnAction) action;
                return performRightTurnAction(rightTurnAction);
            }
            if (action instanceof MoveAction) {
                final MoveAction moveAction = (MoveAction) action;
                return performMoveAction(moveAction);
            }
            if (action instanceof ReportAction) {
                final ReportAction reportTurnAction = (ReportAction) action;
                return performReportAction(reportTurnAction);
            }
        } catch (IllegalActionException e) {
            return new ActionIgnored();
        }
        return new ActionIgnored();
    }

    private Outcome performPlaceAction(final PlaceAction action) throws IllegalActionException {
        // Simulation currently only supports place ONCE.
        // To support multiple PLACE commands then use Board.changePosition instead of occupy
        if (boardPosition != null) {
            throw new IllegalActionException("Robot has already been placed");
        }

        boardPosition = board.occupy(new BoardPosition(action.getX(), action.getY()));
        facingDirection = action.getDirection();

        return new Outcome();
    }

    private Outcome performLeftTurnAction(final LeftTurnAction action) throws IllegalActionException {
        if (boardPosition == null) {
            throw new IllegalActionException("Robot has not been placed");
        }

        facingDirection = facingDirection.left();

        return new Outcome();
    }

    private Outcome performRightTurnAction(final RightTurnAction action) throws IllegalActionException {
        if (boardPosition == null) {
            throw new IllegalActionException("Robot has not been placed");
        }

        facingDirection = facingDirection.right();

        return new Outcome();
    }

    private Outcome performMoveAction(final MoveAction action) throws IllegalActionException {
        if (boardPosition == null) {
            throw new IllegalActionException("Robot has not been placed");
        }

        boardPosition = board.changePosition(boardPosition, board.getRelativePosition(boardPosition, facingDirection, 1));

        return new Outcome();
    }

    private Outcome performReportAction(final ReportAction action) throws IllegalActionException {
        if (boardPosition == null) {
            throw new IllegalActionException("Robot has not been placed");
        }
        return new Outcome(String.format("%d,%d,%s", boardPosition.getX(), boardPosition.getY(), facingDirection));
    }

}
