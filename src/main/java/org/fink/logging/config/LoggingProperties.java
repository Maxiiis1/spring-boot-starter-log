package org.fink.logging.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.logging")
public class LoggingProperties {
    private boolean enabled = true;
    private String level = "INFO";
}