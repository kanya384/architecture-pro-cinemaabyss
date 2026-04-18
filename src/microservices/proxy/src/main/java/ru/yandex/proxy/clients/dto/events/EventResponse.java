package ru.yandex.proxy.clients.dto.events;

import lombok.Builder;

@Builder
public record EventResponse(
        String status,
        Integer partition,
        Integer offset,
        Event event
) {
}
