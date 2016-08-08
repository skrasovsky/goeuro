package com.goeuro.core;

/**
 * Thrown to indicate that API command context is invalid.
 */
public class InvalidCommandContextException extends IllegalArgumentException {

    public InvalidCommandContextException() {
        super();
    }

    public InvalidCommandContextException(String message) {
        super(message);
    }

    public InvalidCommandContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCommandContextException(Throwable cause) {
        super(cause);
    }

}
