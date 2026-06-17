package com.otimizza.teste.domain.strategies;

import com.otimizza.teste.domain.entities.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SimpleReorderStrategyTest {

    private final ReorderStrategy strategy = new SimpleReorderStrategy();

    @Test
    @DisplayName("Should sort tasks by position ascending")
    void shouldSortTasksByPosition() {
        String columnId = UUID.randomUUID().toString();
        Task t1 = Task.builder().name("Task 1").position(2).columnId(columnId).build();
        Task t2 = Task.builder().name("Task 2").position(0).columnId(columnId).build();
        Task t3 = Task.builder().name("Task 3").position(1).columnId(columnId).build();

        List<Task> sorted = strategy.reorder(List.of(t1, t2, t3));

        assertEquals(0, sorted.get(0).position());
        assertEquals(1, sorted.get(1).position());
        assertEquals(2, sorted.get(2).position());
    }

    @Test
    @DisplayName("Should return empty list when input is empty")
    void shouldHandleEmptyList() {
        List<Task> result = strategy.reorder(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should return single-element list unchanged")
    void shouldHandleSingleElement() {
        Task task = Task.builder().name("Task").position(5).columnId(UUID.randomUUID().toString()).build();
        List<Task> result = strategy.reorder(List.of(task));
        assertEquals(1, result.size());
        assertEquals(5, result.get(0).position());
    }
}
