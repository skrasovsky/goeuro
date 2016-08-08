package com.goeuro.service.logger;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * A custom console logger is used to log message.
 * Logger uses a simple format of log, without any date, time, class name et al. fields.
 */
@Component
public class CustomConsoleLogger {

    private static Logger logger;

    private static final String WARN_PREFIX = "[WARN] ";

    private static final String ERROR_PREFIX = "[ERROR] ";

    @PostConstruct
    private void init() {
        logger = Logger.getLogger(CustomConsoleLogger.class.getName());
        logger.setUseParentHandlers(false);

        CustomConsoleFormatter customConsoleFormatter = new CustomConsoleFormatter();
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(customConsoleFormatter);
        logger.addHandler(handler);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.info(WARN_PREFIX + message);
    }

    public static void error(String message) {
        logger.info(ERROR_PREFIX + message);
    }

    public static String message(String messageTemplate, Object... args) {
        return String.format(messageTemplate, args);
    }
}
