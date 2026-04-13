package ru.yandex.events.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record UserEvent(
        @JsonProperty("user_id")
        Integer userId,
        String username,
        String email,
        String action,
        String timestamp
) {
}
