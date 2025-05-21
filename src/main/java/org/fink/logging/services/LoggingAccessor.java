package org.fink.logging.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fink.logging.config.LoggingProperties;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

@Slf4j
@Component
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

    @Override
    public boolean checkLoggingState() {
        return loggingProperties.isEnabled();
    }
}