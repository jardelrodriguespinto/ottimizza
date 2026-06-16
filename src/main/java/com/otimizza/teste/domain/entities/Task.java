package com.otimizza.teste.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Task {
    private final UUID id;
    private final String name;
    private final int position;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime dueDate;
    private final boolean completed;
    private final List<String> tags;
    private final UUID columnId;

    public Task(UUID id, String name, int position, OffsetDateTime createdAt, 
                OffsetDateTime dueDate, boolean completed, List<String> tags, UUID columnId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name cannot be null or blank");
        }
        if (id == null) {
            throw new IllegalArgumentException("Task id cannot be null");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Task createdAt cannot be null");
        }
        if (columnId == null) {
            throw new IllegalArgumentException("Column id cannot be null");
        }
        this.id = id;
        this.name = name;
        this.position = position;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
        this.completed = completed;
        this.tags = tags != null ? List.copyOf(tags) : List.of();
        this.columnId = columnId;
    }
}
