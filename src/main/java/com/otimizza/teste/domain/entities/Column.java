package com.otimizza.teste.domain.entities;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@Builder
public class Column implements Serializable {
    private String id;
    private String name;
    private int position;
    private String boardId;


    public String id() { return id; }
    public String name() { return name; }
    public int position() { return position; }
    public String boardId() { return boardId; }

    public Column(String id, String name, int position, String boardId) {
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
