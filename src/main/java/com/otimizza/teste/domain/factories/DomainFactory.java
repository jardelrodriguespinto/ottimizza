package com.otimizza.teste.domain.factories;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.entities.Task;

import java.util.UUID;

public class DomainFactory {

    public static Board createBoard(String name) {
        return new Board(UUID.randomUUID(), name);
    }

    public static Column createColumn(String name, int position, UUID boardId) {
        return new Column(UUID.randomUUID(), name, position, boardId);
    }

    public static Task createTask(String name, int position, UUID columnId) {
        return Task.builder()
                .name(name)
                .position(position)
                .columnId(columnId)
                .build();
    }
}
