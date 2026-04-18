package ru.yandex.proxy.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.yandex.proxy.clients.dto.events.EventResponse;
import ru.yandex.proxy.clients.dto.events.MovieEvent;
import ru.yandex.proxy.config.AppConfig;

@Component
@RequiredArgsConstructor
public class EventsClient {
    private final WebClient webClient;
    private final AppConfig appConfig;

    public Mono<EventResponse> process(MovieEvent event) {
        return webClient
                .post()
                .uri(appConfig.getEventsServiceUrl())
                .bodyValue(event)
                .retrieve()
                .bodyToMono(EventResponse.class);
    }
}
