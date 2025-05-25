package org.fink.logging.services;

import org.slf4j.event.Level;

public interface ILoggingAccessor {
    Level getConvertedLevel();
}