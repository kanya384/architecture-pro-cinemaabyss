package ru.yandex.proxy.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.proxy.clients.EventsClient;
import ru.yandex.proxy.clients.RestProxyClient;
import ru.yandex.proxy.config.AppConfig;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class ProxyService {
    private final AppConfig appConfig;
    private final RestProxyClient monolithClient;
    private final RestProxyClient moviesClient;
    private final EventsClient eventsClient;

    public ProxyService(AppConfig appConfig, WebClient webClient, EventsClient eventsClient) {
        this.appConfig = appConfig;
        this.monolithClient = new RestProxyClient(webClient, appConfig.getMonolithUrl());
        this.moviesClient = new RestProxyClient(webClient, appConfig.getMoviesServiceUrl());
        this.eventsClient = eventsClient;
    }

    public <T> Mono<T> proxyGet(Map<String, String> headers, String endpoint, Class<T> responseType, boolean direct) {
        if (direct) {
            return monolithClient.processGet(headers, endpoint, responseType);
        }

        var uuid = UUID.randomUUID();
        log.info("received GET request (headers: {}, endpoint: {})", headers, endpoint);

        if (appConfig.getGradualMigration() == false) {
            log.info("({}) migration off, proxying request to monilith", uuid);
            return monolithClient.processGet(headers, endpoint, responseType);
        }

        var percent = appConfig.getMoviesMigrationPercent();

        Random random = new Random();
        Double randomValue = random.nextDouble(0, 100);
        if (percent > randomValue) {
            log.info("({}) proxying request to movies service", uuid);
            return moviesClient.processGet(headers, endpoint, responseType);
        }

        log.info("({}) migration off, proxying request to monilith", uuid);
        return monolithClient.processGet(headers, endpoint, responseType);
    }

    public <T, R> Mono<T> proxyPost(Map<String, String> headers, String endpoint, R body, Class<T> responseType, boolean direct) {
        if (direct) {
            return monolithClient.processPost(headers, endpoint, body, responseType);
        }

        var uuid = UUID.randomUUID();
        log.info("({}) received POST request (headers: {}, endpoint: {}, body: {})", uuid, headers, endpoint, body);

        if (appConfig.getGradualMigration() == false) {
            log.info("({}) migration off, proxying request to monilith", uuid);
            return monolithClient.processPost(headers, endpoint, body, responseType);
        }

        var percent = appConfig.getMoviesMigrationPercent();

        Random random = new Random();
        Double randomValue = random.nextDouble(0, 100);
        if (percent > randomValue) {
            log.info("({}) proxying request to movies service", uuid);
            return moviesClient.processPost(headers, endpoint, body, responseType);
        }

        log.info("({}) proxying request to monolith service", uuid);
        return monolithClient.processPost(headers, endpoint, body, responseType);
    }
}
