package com.optima.test_task.dtos;

import com.optima.test_task.enums.PaymentProvider;
import com.optima.test_task.enums.PaymentType;

import java.util.Map;
import java.util.Objects;

public record CreatePaymentInput(PaymentProvider paymentProvider,
                                 Map<String, Object> paymentDetails) {
    public CreatePaymentInput {
        Objects.requireNonNull(paymentProvider, "Поставщик оплаты не может быть null");
        Objects.requireNonNull(paymentDetails, "Детали оплаты не могут быть null");
    }
}