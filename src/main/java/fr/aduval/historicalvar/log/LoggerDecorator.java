package fr.aduval.historicalvar.log;

import lombok.Setter;
import org.slf4j.Logger;

/**
 * A logger decorator that can silence logging in a global way when verbose mode is disabled
 */
public class LoggerDecorator {

    @Setter
    private boolean verbose;

    private static final LoggerDecorator INSTANCE = new LoggerDecorator();

    public static LoggerDecorator getInstance() {
        return INSTANCE;
    }

    public void trace(Logger logger, String text, Object... objects) {
        if(!verbose) return;
        logger.trace(text, objects);
    }

    public void debug(Logger logger, String text, Object... objects) {
        if(!verbose) return;
        logger.debug(text, objects);
    }

    public void info(Logger logger, String text, Object... objects) {
        if(!verbose) return;
        logger.info(text, objects);
    }

    public void warn(Logger logger, String text, Object... objects) {
        if(!verbose) return;
        logger.warn(text, objects);
    }

    public void error(Logger logger, String text, Object... objects) {
        if(!verbose) return;
        logger.error(text, objects);
    }

}
