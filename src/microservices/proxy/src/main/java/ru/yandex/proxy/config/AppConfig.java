package ru.yandex.proxy.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AppConfig {
    @Value("${MONOLITH_URL}")
    private String monolithUrl;
    @Value("${MOVIES_SERVICE_URL}")
    private String moviesServiceUrl;
    @Value("${EVENTS_SERVICE_URL}")
    private String eventsServiceUrl;
    @Value("${GRADUAL_MIGRATION}")
    private Boolean gradualMigration;
    @Value("${MOVIES_MIGRATION_PERCENT}")
    private Integer moviesMigrationPercent;
}
