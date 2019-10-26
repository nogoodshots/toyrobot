package net.nogoodshots.toyrobot;

/** Consumer or handler of {@link net.nogoodshots.toyrobot.Action}.
 * This interface is implemented by Simulation models or executives.
 *
 */
public interface ActionHandler {

    /**
     * Pass the {@link Action} to the Simulation specific model for consumption.
     * @param action Describes a Simulation interaction. Should not be null.
     */
    void handleAction(final Action action);
}
