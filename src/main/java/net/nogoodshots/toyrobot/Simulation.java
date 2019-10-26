package net.nogoodshots.toyrobot;

import java.io.OutputStream;
import java.util.*;

public class Simulation {

    // This simulation has single Robot, if multiple robots need to be supported use a collection instead of a single instance.
    private Robot robot;
    private final Board board;
    private final OutputStream outputStream;

    public Simulation(final Board board, final OutputStream outputStream) {
        Objects.requireNonNull(board, "Simulation cannot accept a null Board");
        Objects.requireNonNull(outputStream, "Simulation cannot accept a null OutputStream");
        this.board = board;
        this.outputStream = outputStream;
    }

    // Default to stdout
    public Simulation(final Board board) {
        this(board, System.out);
    }

    public Optional<Robot> getRobot() {
        return Optional.ofNullable(robot);
    }

    public Outcome takeAction(final Action action) {
        Objects.requireNonNull(action, "Simulation cannot accept a null Action");
        if (action instanceof RobotAction) {
            if (robot == null) {
                robot = new Robot(board);
            }

            final RobotAction robotAction = (RobotAction) action;
            // Perform the action, ignore any invalid actions
            return robot.performAction(robotAction);
        } else {
            return new ActionIgnored();
        }
    }

}
