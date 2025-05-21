package org.fink.logging.config;

import lombok.RequiredArgsConstructor;
import org.fink.logging.aspects.LogAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import services.ILoggingAccessor;
import services.LoggingAccessor;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
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