package com.otimizza.teste.domain.factories;

import com.otimizza.teste.application.dtos.BoardRequest;
import com.otimizza.teste.application.dtos.ColumnRequest;
import com.otimizza.teste.application.dtos.TaskRequest;
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

    public static Board createBoard(BoardRequest request) {
        return createBoard(request.name());
    }

    public static Column createColumn(String name, int position, String boardId) {
        return new Column(UUID.randomUUID().toString(), name, position, boardId);
    }

    public static Column createColumn(ColumnRequest request) {
        return createColumn(request.name(), request.position(), request.boardId());
    }

    public static Task createTask(TaskRequest taskRequest) {
        return Task.builder()
                .id(UUID.randomUUID().toString())
                .name(taskRequest.name())
                .position(taskRequest.position())
                .columnId(taskRequest.columnId())
                .createdAt(taskRequest.createdAt() != null ? taskRequest.createdAt() : OffsetDateTime.now(ZoneOffset.UTC))
                .dueDate(taskRequest.dueDate())
                .completed(taskRequest.completed())
                .tags(taskRequest.tags() != null ? taskRequest.tags() : List.of())
                .build();
    }
}
