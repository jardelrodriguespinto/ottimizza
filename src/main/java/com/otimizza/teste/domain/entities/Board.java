package com.otimizza.teste.domain.entities;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@Builder
public class Board implements Serializable {
    private String id;
    private String name;

    public String id() { return id; }
    public String name() { return name; }

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
