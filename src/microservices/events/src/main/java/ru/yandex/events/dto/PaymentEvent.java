package ru.yandex.events.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Builder
public record PaymentEvent(
        @JsonProperty("payment_id")
        Integer paymentId,
        @JsonProperty("user_id")
        Integer userId,
        BigDecimal amount,
        String status,
        Timestamp timestamp,
        @JsonProperty("method_type")
        String methodType
) {
}
