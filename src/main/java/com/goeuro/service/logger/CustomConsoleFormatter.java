package com.goeuro.service.logger;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Simple custom format of log message.
 * Example: '[GOEURO]# text of message'.
 */
public class CustomConsoleFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return "\n" + "[GOEURO]# " + formatMessage(record);
    }

    public String getHead(Handler handler) {
        return super.getHead(handler);
    }

    public String getTail(Handler handler) {
        return super.getTail(handler);
    }
}
