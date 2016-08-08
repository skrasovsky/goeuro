package com.goeuro.core;

/**
 * Thrown to indicate that API command execution is failed.
 */
public class CommandProcessException extends RuntimeException {

    public CommandProcessException() {
        super();
    }

    public CommandProcessException(String message) {
        super(message);
    }

    public CommandProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandProcessException(Throwable cause) {
        super(cause);
    }

}
