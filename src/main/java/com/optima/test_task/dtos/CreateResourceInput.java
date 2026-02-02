package com.optima.test_task.dtos;

import java.math.BigDecimal;
import java.util.Objects;

public record CreateResourceInput(String name, String description, BigDecimal amount, Boolean isActive) {
    public CreateResourceInput{
        Objects.requireNonNull(name, "Название не может быть null");
        Objects.requireNonNull(description, "Описание не может быть null");
        Objects.requireNonNull(amount, "Цена в час не может быть null");
        Objects.requireNonNull(isActive, "Состояние ресурса не может быть null");
    }
}
