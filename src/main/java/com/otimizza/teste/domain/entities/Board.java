package com.otimizza.teste.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Board {
    private final UUID id;
    private final String name;

    public Board(UUID id, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Board name cannot be null or blank");
        }
        if (id == null) {
            throw new IllegalArgumentException("Board id cannot be null");
        }
        this.id = id;
        this.name = name;
    }
}
