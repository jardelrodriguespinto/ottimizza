package com.otimizza.teste.domain.entities;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record Task(
        UUID id,
        String name,
        int position,
        OffsetDateTime createdAt,
        OffsetDateTime dueDate,
        boolean completed,
        List<String> tags,
        UUID columnId
) {
    public Task {
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
        tags = tags != null ? List.copyOf(tags) : List.of();
    }
}
