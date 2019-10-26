package net.nogoodshots.toyrobot;

import java.util.Objects;
import java.util.Optional;

/** Outcome is the visible result of an Action being taken.
 * Today the only visible result is a message. Actions that result in some intervention or the
 * termination of the simulation may be implemented later.
 */
public class Outcome {

    protected final String message;

    public Outcome(final String message) {
        Objects.requireNonNull(message);
        this.message = message;
    }

    public Outcome() {
        this.message = null;
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }
}
