package com.otimizza.teste.domain.factories;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.entities.Task;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class DomainFactory {

    public static Board createBoard(String name) {
        return new Board(UUID.randomUUID().toString(), name);
    }

    public static Column createColumn(String name, int position, String boardId) {
        return new Column(UUID.randomUUID().toString(), name, position, boardId);
    }

    public static Task createTask(String name, int position, String columnId,
                                  OffsetDateTime createdAt, OffsetDateTime dueDate,
                                  boolean completed, List<String> tags) {
        return Task.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .position(position)
                .columnId(columnId)
                .createdAt(createdAt != null ? createdAt : OffsetDateTime.now(ZoneOffset.UTC))
                .dueDate(dueDate)
                .completed(completed)
                .tags(tags != null ? tags : List.of())
                .build();
    }
}
