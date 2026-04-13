package ru.yandex.events.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventResponse(
        String status,
        Long offset,
        Integer partition,
        Object event
) {
}
