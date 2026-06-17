package com.otimizza.teste.application.usecases;

import com.otimizza.teste.application.events.TaskCreatedEvent;
import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.factories.DomainFactory;
import com.otimizza.teste.domain.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskUseCase {
    private final TaskRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Cacheable(value = "tasks", key = "#columnId")
    public List<Task> listByColumn(String columnId) {
        return repository.findByColumnId(java.util.UUID.fromString(columnId));
    }

    public Optional<Task> findById(String id) {
        return repository.findById(java.util.UUID.fromString(id));
    }

    @CacheEvict(value = "tasks", key = "#columnId")
    public Task create(String name, int position, String columnId) {
        Task task = DomainFactory.createTask(name, position, columnId);
        Task savedTask = repository.save(task);
        eventPublisher.publishEvent(new TaskCreatedEvent(savedTask));
        return savedTask;
    }

    @CacheEvict(value = "tasks", key = "#columnId")
    public Task update(String id, String name, int position, String columnId, boolean completed, List<String> tags) {
        Task existingTask = repository.findById(java.util.UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        
        Task updatedTask = existingTask.toBuilder()
                .name(name)
                .position(position)
                .columnId(columnId)
                .completed(completed)
                .tags(tags)
                .build();
        
        return repository.save(updatedTask);
    }

    @CacheEvict(value = "tasks", key = "#columnId")
    public void delete(String id, String columnId) {
        repository.deleteById(java.util.UUID.fromString(id));
    }
}
