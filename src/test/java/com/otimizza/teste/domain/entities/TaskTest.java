package com.otimizza.teste.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    @DisplayName("Should create a task with all fields")
    void shouldCreateTaskWithAllFields() {
        String id = UUID.randomUUID().toString();
        String columnId = UUID.randomUUID().toString();
        OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

        Task task = Task.builder()
                .id(id)
                .name("Implementar autenticação")
                .position(0)
                .createdAt(createdAt)
                .dueDate(createdAt.plusDays(5))
                .completed(false)
                .tags(List.of("backend", "segurança"))
                .columnId(columnId)
                .build();

        assertEquals(id, task.id());
        assertEquals("Implementar autenticação", task.name());
        assertEquals(0, task.position());
        assertEquals(columnId, task.columnId());
        assertFalse(task.completed());
        assertEquals(2, task.tags().size());
    }

    @Test
    @DisplayName("Should auto-generate id and createdAt when null")
    void shouldAutoGenerateIdAndCreatedAt() {
        Task task = Task.builder()
                .name("Tarefa")
                .position(0)
                .columnId(UUID.randomUUID().toString())
                .build();

        assertNotNull(task.id());
        assertNotNull(task.createdAt());
        assertNotNull(task.tags());
        assertTrue(task.tags().isEmpty());
    }

    @Test
    @DisplayName("Should throw when name is blank")
    void shouldThrowWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () ->
                Task.builder().name("").columnId(UUID.randomUUID().toString()).build());
    }

    @Test
    @DisplayName("Should throw when columnId is null")
    void shouldThrowWhenColumnIdIsNull() {
        assertThrows(IllegalArgumentException.class, () ->
                Task.builder().name("Tarefa").columnId(null).build());
    }

    @Test
    @DisplayName("Should return immutable tags list")
    void shouldReturnImmutableTagsList() {
        Task task = Task.builder()
                .name("Tarefa")
                .columnId(UUID.randomUUID().toString())
                .tags(List.of("tag1"))
                .build();

        assertThrows(UnsupportedOperationException.class, () -> task.tags().add("tag2"));
    }
}
