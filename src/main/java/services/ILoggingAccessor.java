package services;

import org.slf4j.event.Level;

public interface ILoggingAccessor {
    Level getConvertedLevel();
    boolean checkLoggingState();
}