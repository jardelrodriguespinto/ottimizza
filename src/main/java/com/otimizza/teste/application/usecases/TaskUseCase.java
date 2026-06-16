package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class TaskUseCase {
    private final TaskRepository repository;

    public List<Task> listByColumn(UUID columnId) {
        return repository.findByColumnId(columnId);
    }

    public Task create(String name, int position, UUID columnId) {
        Task task = new Task(UUID.randomUUID(), name, position, OffsetDateTime.now(), null, false, List.of(), columnId);
        return repository.save(task);
    }
}
