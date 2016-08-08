package com.goeuro.service.csv;

/**
 * Thrown to indicate that process if creation csv file is failed.
 */
public class CSVFileCreationException extends RuntimeException {

    public CSVFileCreationException() {
        super();
    }

    public CSVFileCreationException(String message) {
        super(message);
    }

    public CSVFileCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CSVFileCreationException(Throwable cause) {
        super(cause);
    }

}
