package com.otimizza.teste.domain.entities;

public record Column(String id, String name, int position, String boardId) {
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
