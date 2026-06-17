package com.otimizza.teste.application.usecases;

import com.otimizza.teste.application.events.TaskCreatedEvent;
import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import com.otimizza.teste.domain.repositories.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskUseCaseTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private ColumnRepository columnRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TaskUseCase taskUseCase;

    @Test
    @DisplayName("Should list tasks by column ID")
    void shouldListTasksByColumn() {
        String columnId = UUID.randomUUID().toString();
        Task task = Task.builder().name("Task 1").columnId(columnId).build();
        when(repository.findByColumnId(columnId)).thenReturn(List.of(task));

        List<Task> tasks = taskUseCase.listByColumn(columnId);

        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        verify(repository).findByColumnId(columnId);
    }

    @Test
    @DisplayName("Should create a task and publish event")
    void shouldCreateTaskAndPublishEvent() {
        String columnId = UUID.randomUUID().toString();
        when(columnRepository.findById(columnId)).thenReturn(Optional.of(new Column(columnId, "Column", 0, "Board")));
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task created = taskUseCase.create("Task 1", 0, columnId, null, null, false, null);

        assertNotNull(created);
        assertEquals("Task 1", created.name());
        verify(repository).save(any(Task.class));
        verify(eventPublisher).publishEvent(any(TaskCreatedEvent.class));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when column not found on create")
    void shouldThrowWhenColumnNotFoundOnCreate() {
        String columnId = UUID.randomUUID().toString();
        when(columnRepository.findById(columnId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> taskUseCase.create("Task", 0, columnId, null, null, false, null));
    }

    @Test
    @DisplayName("Should update a task")
    void shouldUpdateTask() {
        String taskId = UUID.randomUUID().toString();
        String columnId = UUID.randomUUID().toString();
        Task existing = Task.builder().id(taskId).name("Old").position(0).columnId(columnId).build();
        when(columnRepository.findById(columnId)).thenReturn(Optional.of(new Column(columnId, "Column", 0, "Board")));
        when(repository.findById(taskId)).thenReturn(Optional.of(existing));
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task updated = taskUseCase.update(taskId, "New", 1, columnId, null, true, List.of("tag"));

        assertEquals("New", updated.name());
        assertTrue(updated.completed());
        verify(repository).save(any(Task.class));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when task not found on update")
    void shouldThrowWhenTaskNotFoundOnUpdate() {
        String taskId = UUID.randomUUID().toString();
        String columnId = UUID.randomUUID().toString();
        when(columnRepository.findById(columnId)).thenReturn(Optional.of(new Column(columnId, "Column", 0, "Board")));
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> taskUseCase.update(taskId, "Name", 0, columnId, null, false, null));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when column not found on update")
    void shouldThrowWhenColumnNotFoundOnUpdate() {
        String taskId = UUID.randomUUID().toString();
        String columnId = UUID.randomUUID().toString();
        when(columnRepository.findById(columnId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> taskUseCase.update(taskId, "Name", 0, columnId, null, false, null));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when task not found on delete")
    void shouldThrowWhenTaskNotFoundOnDelete() {
        String taskId = UUID.randomUUID().toString();
        when(repository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> taskUseCase.delete(taskId));
    }
}
