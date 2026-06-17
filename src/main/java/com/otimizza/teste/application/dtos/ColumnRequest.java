package com.otimizza.teste.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ColumnRequest(
        @NotBlank(message = "Name cannot be blank") String name,
        int position,
        @NotNull(message = "Board ID cannot be null") String boardId) {}
