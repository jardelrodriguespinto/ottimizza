package com.otimizza.teste.application.dtos;

import jakarta.validation.constraints.NotBlank;

public record BoardRequest(@NotBlank(message = "Name cannot be blank") String name) {}
