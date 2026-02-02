package com.optima.test_task.dtos;

import com.optima.test_task.enums.Role;

import java.util.Objects;

public record UserResponse(Long id, String username, String fullName, Role role) {
    public UserResponse {
        Objects.requireNonNull(username, "Никнейм не может быть null");
        Objects.requireNonNull(fullName, "Имя не может быть null");
        Objects.requireNonNull(role, "Роль не может быть null");
    }
}
