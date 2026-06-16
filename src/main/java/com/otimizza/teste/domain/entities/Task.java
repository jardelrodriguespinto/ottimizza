package com.otimizza.teste.domain.entities;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
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
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = OffsetDateTime.now(java.time.ZoneOffset.UTC);
        }
        if (columnId == null) {
            throw new IllegalArgumentException("Column id cannot be null");
        }
        if (tags == null) {
            tags = List.of();
        } else {
            tags = List.copyOf(tags);
        }
    }
}
