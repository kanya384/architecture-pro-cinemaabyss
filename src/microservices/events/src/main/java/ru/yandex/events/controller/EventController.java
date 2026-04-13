package ru.yandex.events.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.yandex.events.dto.EventResponse;
import ru.yandex.events.dto.MovieEvent;
import ru.yandex.events.dto.PaymentEvent;
import ru.yandex.events.dto.UserEvent;
import ru.yandex.events.service.EventsSenderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private final EventsSenderService eventsSenderService;

    @PostMapping("/movie")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EventResponse> process(@RequestBody MovieEvent event) {
        return eventsSenderService.processEvent(event);
    }

    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EventResponse> process(@RequestBody PaymentEvent event) {
        return eventsSenderService.processEvent(event);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EventResponse> process(@RequestBody UserEvent event) {
        return eventsSenderService.processEvent(event);
    }
}
