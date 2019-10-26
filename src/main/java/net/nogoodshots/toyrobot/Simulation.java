package net.nogoodshots.toyrobot;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/** The model, represenation and executive of the simulation.
 *
 */
public class Simulation {

    // This simulation has single Robot, if multiple robots need to be supported use a collection instead of a single instance.
    private Robot robot;
    private final Board board;
    private final OutputStream outputStream;

    /**
     * Construct a new {@link Simulation} with a {@link Board}.
     * @param board Model of space in the simulation. This should not be null.
     * @param outputStream {@link OutputStream} with which the simulation can interact. This should not be null.
     */
    public Simulation(final Board board, final OutputStream outputStream) {
        Objects.requireNonNull(board, "Simulation cannot accept a null Board");
        Objects.requireNonNull(outputStream, "Simulation cannot accept a null OutputStream");
        this.board = board;
        this.outputStream = outputStream;
    }

    /**
     * Construct a new {@link Simulation} with a {@link Board}. Use StdOut as its {@link OutputStream}
     * @param board Model of space in the simulation. This should not be null.
     */
    public Simulation(final Board board) {
        this(board, System.out);
    }

    public Optional<Robot> getRobot() {
        return Optional.ofNullable(robot);
    }

    /** Interact with the simulation.
     *
     * @param action Interaction requested. This should not be null.
     * @return {@link Outcome} describing the visible result of the given {@link Action}.
     * @throws IOException is the simulation cannot report via the {@link OutputStream}.
     */
    public Outcome takeAction(final Action action) throws IOException {
        Objects.requireNonNull(action, "Simulation cannot accept a null Action");
        if (action instanceof RobotAction) {
            if (robot == null) {
                robot = new Robot(board);
            }

            final RobotAction robotAction = (RobotAction) action;
            // Perform the action, ignore any invalid actions
            final Outcome outcome = robot.performAction(robotAction);
            if (outcome.getMessage().isPresent()) {
                outputStream.write(String.format("Output: %s\n", outcome.getMessage().get()).getBytes());
            }
            return outcome;
        } else {
            return new ActionIgnored();
        }
    }

}
