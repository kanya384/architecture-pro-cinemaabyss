package ru.yandex.events.enums;

import lombok.Getter;

@Getter
public enum EventStatus {
    SUCCESS("success"),
    ERROR("error");

    private final String value;

    EventStatus(String value) {
        this.value = value;
    }
}
