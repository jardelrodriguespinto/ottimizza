package com.otimizza.teste.domain.entities;

import java.util.UUID;

public record Board(UUID id, String name) {
    public Board {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Board name cannot be null or blank");
        }
        if (id == null) {
            throw new IllegalArgumentException("Board id cannot be null");
        }
    }
}
