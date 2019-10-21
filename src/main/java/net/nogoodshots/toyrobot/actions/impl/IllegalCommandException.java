package net.nogoodshots.toyrobot.actions.impl;

import java.util.Objects;

public class IllegalCommandException extends RuntimeException {

    public IllegalCommandException(final String message) {
        super(message);
    }
}
