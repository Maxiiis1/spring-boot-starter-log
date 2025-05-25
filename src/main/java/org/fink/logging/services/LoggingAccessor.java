package org.fink.logging.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fink.logging.config.LoggingProperties;
import org.slf4j.event.Level;

@Slf4j
@RequiredArgsConstructor
public class LoggingAccessor implements ILoggingAccessor {

    private final LoggingProperties loggingProperties;

    @Override
    public Level getConvertedLevel() {
        String logLevel = loggingProperties.getLevel();

        if (logLevel == null) {
            return Level.INFO;
        }

        try {
            return Level.valueOf(logLevel.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid log level: '{}'. Defaulting to INFO.", logLevel);
            return Level.INFO;
        }
    }
}