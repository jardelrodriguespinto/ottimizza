package com.otimizza.teste.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@Accessors(fluent = true)
public class Board implements Serializable {
    private String id;
    private String name;

    @Builder
    public Board(String id, String name) {
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
