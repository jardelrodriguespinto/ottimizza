package com.otimizza.teste.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Column {
    private final UUID id;
    private final String name;
    private final int position;
    private final UUID boardId;

    public Column(UUID id, String name, int position, UUID boardId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Column name cannot be null or blank");
        }
        if (id == null) {
            throw new IllegalArgumentException("Column id cannot be null");
        }
        if (boardId == null) {
            throw new IllegalArgumentException("Board id cannot be null");
        }
        this.id = id;
        this.name = name;
        this.position = position;
        this.boardId = boardId;
    }
}
