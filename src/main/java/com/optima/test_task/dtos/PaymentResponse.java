package com.optima.test_task.dtos;

import com.optima.test_task.enums.PaymentProvider;
import com.optima.test_task.enums.PaymentStatus;
import com.optima.test_task.enums.PaymentType;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public record PaymentResponse(
        Long id,
        BookingResponse booking,
        PaymentStatus paymentStatus,
        PaymentType paymentType,
        Map<String, Object> paymentDetails,
        PaymentProvider paymentProvider
) {
    public PaymentResponse {
        Objects.requireNonNull(id, "ID платежа не может быть null");
        Objects.requireNonNull(booking, "Информация о бронировании не может быть null");
        Objects.requireNonNull(paymentStatus, "Статус платежа не может быть null");
        Objects.requireNonNull(paymentType, "Тип платежа не может быть null");
        Objects.requireNonNull(paymentProvider, "Платежный провайдер не может быть null");
        Objects.requireNonNull(paymentDetails, "Детали платежа не могут быть null (ожидается хотя бы пустой объект)");
    }
}