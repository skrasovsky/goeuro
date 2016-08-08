package com.goeuro.core;

/**
 * Thrown to indicate that API command is not found.
 */
public class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException() {
        super();
    }

    public CommandNotFoundException(String message) {
        super(message);
    }

    public CommandNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNotFoundException(Throwable cause) {
        super(cause);
    }

}
