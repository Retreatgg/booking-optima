package com.optima.test_task.dtos;

import com.optima.test_task.enums.PaymentType;

import java.time.OffsetDateTime;
import java.util.Objects;

public record CreateBookingInput(
        Long resourceId,
        OffsetDateTime startTime, OffsetDateTime endTime,
        CreatePaymentInput paymentInput, PaymentType paymentType) {
    public CreateBookingInput {
        Objects.requireNonNull(resourceId, "Ресурс ID не может быть null");
        Objects.requireNonNull(startTime, "Время начала не может быть null");
        Objects.requireNonNull(endTime, "Время окончания не может быть null");
        Objects.requireNonNull(paymentInput, "Детали оплаты не могут быть null");
        Objects.requireNonNull(paymentType, "Способ оплаты не может быть null");
    }
}
