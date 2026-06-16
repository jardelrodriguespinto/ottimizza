package com.otimizza.teste.domain.entities;

import java.util.UUID;

public record Column(UUID id, String name, int position, UUID boardId) {
    public Column {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Column name cannot be null or blank");
        }
        if (id == null) {
            throw new IllegalArgumentException("Column id cannot be null");
        }
        if (boardId == null) {
            throw new IllegalArgumentException("Board id cannot be null");
        }
    }
}
