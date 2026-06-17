package com.otimizza.teste.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

public record TaskRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        int position,
        OffsetDateTime createdAt,
        OffsetDateTime dueDate,
        boolean completed,
        List<String> tags,
        @NotNull(message = "Column ID cannot be null") String columnId) {}
