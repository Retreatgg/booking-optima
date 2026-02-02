package com.optima.test_task.dtos;

import java.math.BigDecimal;
import java.util.Objects;

public record ResourceResponse(Long id, String name, String description, Boolean isActive, BigDecimal amount) {
    public ResourceResponse {
        Objects.requireNonNull(id, "ID не может быть null");
        Objects.requireNonNull(name, "Названия ресурса не может быть null");
        Objects.requireNonNull(description, "Описание ресурса не может быть null");
        Objects.requireNonNull(isActive, "Статус может не может быть null");
        Objects.requireNonNull(amount, "Сумма к оплате не может быть null");
    }
}

