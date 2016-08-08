package com.goeuro.core;

/**
 * Thrown to indicate that API command context building failed.
 */
public class CommandContextBuilderException extends IllegalArgumentException {

    public CommandContextBuilderException() {
        super();
    }

    public CommandContextBuilderException(String message) {
        super(message);
    }

    public CommandContextBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandContextBuilderException(Throwable cause) {
        super(cause);
    }

}
