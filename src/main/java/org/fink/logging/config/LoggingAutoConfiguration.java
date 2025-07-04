package org.fink.logging.config;

import lombok.RequiredArgsConstructor;
import org.fink.logging.aspects.LogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.fink.logging.services.ILoggingAccessor;
import org.fink.logging.services.LoggingAccessor;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(name = "app.logging.enabled", havingValue = "true", matchIfMissing = true)
public class LoggingAutoConfiguration {
    private final LoggingProperties properties;

    @Bean
    public ILoggingAccessor loggingAccessor() {
        return new LoggingAccessor(properties);
    }

    @Bean
    public LogAspect logAspect(ILoggingAccessor accessor) {
        return new LogAspect(accessor);
    }
}