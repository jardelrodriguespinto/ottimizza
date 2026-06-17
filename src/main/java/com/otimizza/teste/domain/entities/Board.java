package com.otimizza.teste.domain.entities;

public record Board(String id, String name) {
    public Board {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Board name cannot be null or blank");
        }
        if (id == null) {
            throw new IllegalArgumentException("Board id cannot be null");
        }
    }
}
