package com.otimizza.teste.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Accessors(fluent = true)
public class Task implements Serializable {
    private String id;
    private String name;
    private int position;
    private OffsetDateTime createdAt;
    private OffsetDateTime dueDate;
    private boolean completed;
    private List<String> tags;
    private String columnId;

    @Builder(toBuilder = true)
    public Task(String id, String name, int position, OffsetDateTime createdAt,
                OffsetDateTime dueDate, boolean completed, List<String> tags, String columnId) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name cannot be null or blank");
        }
        this.id = (id == null) ? UUID.randomUUID().toString() : id;
        this.name = name;
        this.position = position;
        this.createdAt = (createdAt == null) ? OffsetDateTime.now(java.time.ZoneOffset.UTC) : createdAt;
        this.dueDate = dueDate;
        this.completed = completed;
        this.tags = (tags == null) ? List.of() : List.copyOf(tags);
        if (columnId == null) {
            throw new IllegalArgumentException("Column id cannot be null");
        }
        this.columnId = columnId;
    }
}
