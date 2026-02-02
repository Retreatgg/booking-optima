package com.optima.test_task.dtos;

import com.optima.test_task.enums.BookingStatus;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

public record BookingResponse(
        Long id,
        ResourceResponse resource,
        OffsetDateTime startTime,
        OffsetDateTime endTime,
        BookingStatus status,
        UserResponse user
) {
    public BookingResponse {
        Objects.requireNonNull(id, "ID бронирования не может быть null");
        Objects.requireNonNull(resource, "Ресурс не может быть null");
        Objects.requireNonNull(startTime, "Время начала бронирования не может быть null");
        Objects.requireNonNull(endTime, "Время окончания бронирования не может быть null");
        Objects.requireNonNull(status, "Статус бронирования не может быть null");
        Objects.requireNonNull(user, "Пользователь не может быть null");
    }
}