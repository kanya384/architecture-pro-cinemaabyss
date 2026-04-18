package ru.yandex.proxy.clients.dto.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record MovieEvent(
        @JsonProperty("movie_id")
        Integer movieId,
        String title,
        String action,
        @JsonProperty("user_id")
        Integer userId,
        Double rating,
        List<String> genres,
        String description
) {
}
