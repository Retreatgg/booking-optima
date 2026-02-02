package com.optima.test_task.dtos;

import java.math.BigDecimal;
import java.util.Objects;

public record UpdateResourceInput(Long id, String name, String description, BigDecimal amount) {
    public UpdateResourceInput {
        Objects.requireNonNull(id, "ID не может быть null");
        Objects.requireNonNull(name, "Названия ресурса не может быть null");
        Objects.requireNonNull(description, "Описание ресурса не может быть null");
        Objects.requireNonNull(amount, "Цена ресурса не может быть null");
    }
}
