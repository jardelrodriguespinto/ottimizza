package com.otimizza.teste.domain.factories;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.entities.Task;

public class DomainFactory {

    public static Board createBoard(String name) {
        return new Board(java.util.UUID.randomUUID().toString(), name);
    }

    public static Column createColumn(String name, int position, String boardId) {
        return new Column(java.util.UUID.randomUUID().toString(), name, position, boardId);
    }

    public static Task createTask(String name, int position, String columnId) {
        return Task.builder()
                .name(name)
                .position(position)
                .columnId(columnId)
                .build();
    }
}
