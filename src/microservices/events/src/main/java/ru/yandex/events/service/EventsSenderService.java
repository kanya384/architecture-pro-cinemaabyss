package ru.yandex.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.yandex.events.dto.EventResponse;
import ru.yandex.events.dto.MovieEvent;
import ru.yandex.events.dto.PaymentEvent;
import ru.yandex.events.dto.UserEvent;
import ru.yandex.events.enums.EventStatus;

@Service
@RequiredArgsConstructor
public class EventsSenderService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Mono<EventResponse> processEvent(MovieEvent event) {
        return process("movie-events", event.movieId().toString(), event);
    }

    public Mono<EventResponse> processEvent(UserEvent event) {
        return process("user-events", event.username(), event);
    }

    public Mono<EventResponse> processEvent(PaymentEvent event) {
        return process("payment-events", event.paymentId().toString(), event);
    }

    private Mono<EventResponse> process(String topic, String key, Object event) {
        return Mono.fromFuture(kafkaTemplate.send(topic, key, event))
                .map(result -> EventResponse.builder()
                        .status(EventStatus.SUCCESS.getValue())
                        .offset(result.getRecordMetadata().offset())
                        .partition(result.getRecordMetadata().partition())
                        .event(event)
                        .build())
                .onErrorReturn(EventResponse.builder()
                        .status(EventStatus.ERROR.getValue())
                        .build());
    }
}
