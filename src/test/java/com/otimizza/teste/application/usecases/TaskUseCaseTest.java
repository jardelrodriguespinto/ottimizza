package com.otimizza.teste.application.usecases;

import com.otimizza.teste.application.events.TaskCreatedEvent;
import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.repositories.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskUseCaseTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private TaskUseCase taskUseCase;

    @Test
    @DisplayName("Should list tasks by column ID")
    void shouldListTasksByColumn() {
        UUID columnId = UUID.randomUUID();
        Task task = Task.builder().name("Task 1").columnId(columnId).build();
        when(repository.findByColumnId(columnId)).thenReturn(List.of(task));

        List<Task> tasks = taskUseCase.listByColumn(columnId);

        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        verify(repository, times(1)).findByColumnId(columnId);
    }

    @Test
    @DisplayName("Should create a task and publish event")
    void shouldCreateTaskAndPublishEvent() {
        UUID columnId = UUID.randomUUID();
        String name = "Task 1";
        int position = 1;

        // DomainFactory.createTask uses Builder, so we need to be careful
        // Mocking the repository to return the task passed to it
        when(repository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task createdTask = taskUseCase.create(name, position, columnId);

        assertNotNull(createdTask);
        assertEquals(name, createdTask.name());
        verify(repository, times(1)).save(any(Task.class));
        verify(eventPublisher, times(1)).publishEvent(any(TaskCreatedEvent.class));
    }
}
