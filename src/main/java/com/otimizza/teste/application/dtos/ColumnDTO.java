package com.otimizza.teste.application.dtos;

import java.util.UUID;

public record ColumnDTO(UUID id, String name, int position, UUID boardId) {}
