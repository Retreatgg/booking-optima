package com.optima.test_task.dtos;

import com.optima.test_task.models.Booking;

import java.util.Objects;

public record SetIsActiveInput(Long id, Boolean isActive) {
    public SetIsActiveInput {
        Objects.requireNonNull(id, "ID не может быть null");
        Objects.requireNonNull(isActive, "Статус не может быть null");
    }
}
