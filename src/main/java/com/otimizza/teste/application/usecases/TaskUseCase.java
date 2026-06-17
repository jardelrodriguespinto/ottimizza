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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskUseCase {
    private final TaskRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    @Cacheable(value = "tasks", key = "#columnId")
    public List<Task> listByColumn(UUID columnId) {
        return repository.findByColumnId(columnId);
    }

    public Optional<Task> findById(UUID id) {
        return repository.findById(id);
    }

    @CacheEvict(value = "tasks", key = "#columnId")
    public Task create(String name, int position, UUID columnId) {
        Task task = DomainFactory.createTask(name, position, columnId);
        Task savedTask = repository.save(task);
        eventPublisher.publishEvent(new TaskCreatedEvent(savedTask));
        return savedTask;
    }

    @CacheEvict(value = "tasks", key = "#columnId")
    public Task update(UUID id, String name, int position, UUID columnId, boolean completed, List<String> tags) {
        Task existingTask = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        
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
    public void delete(UUID id, UUID columnId) {
        repository.deleteById(id);
    }
}
