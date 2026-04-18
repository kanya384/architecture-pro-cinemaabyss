package ru.yandex.events.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.yandex.events.dto.MovieEvent;
import ru.yandex.events.dto.PaymentEvent;
import ru.yandex.events.dto.UserEvent;

@Component
@KafkaListener(topics = {"movie-events", "user-events", "payment-events"})
@Slf4j
public class EventsListener {

    @KafkaHandler
    public void handle(UserEvent event) {
        log.info("received user event {}", event);
    }

    @KafkaHandler
    public void handle(MovieEvent event) {
        log.info("received movie event {}", event);
    }

    @KafkaHandler
    public void handle(PaymentEvent event) {
        log.info("received payment event {}", event);
    }

    @KafkaHandler(isDefault = true)
    public void handle(Object event) {
        log.info("received event{}", event);
    }
}
