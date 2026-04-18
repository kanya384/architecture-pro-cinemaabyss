package ru.yandex.proxy.clients.dto.events;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Event(
        String id,
        String type,
        LocalDateTime timestamp,
        Object payload
) {
}
