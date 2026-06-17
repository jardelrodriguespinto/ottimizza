package com.otimizza.teste.application.usecases;

import com.otimizza.teste.application.events.TaskCreatedEvent;
import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.domain.factories.DomainFactory;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import com.otimizza.teste.domain.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskUseCase {
    private final TaskRepository repository;
    private final ColumnRepository columnRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Cacheable(value = "tasks", key = "#columnId")
    public List<Task> listByColumn(String columnId) {
        return repository.findByColumnId(columnId);
    }

    @CacheEvict(value = "tasks", key = "#columnId")
    public Task create(String name, int position, String columnId,
                       OffsetDateTime createdAt, OffsetDateTime dueDate,
                       boolean completed, List<String> tags) {
        columnRepository.findById(columnId)
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        Task task = DomainFactory.createTask(name, position, columnId, createdAt, dueDate, completed, tags);
        Task saved = repository.save(task);
        eventPublisher.publishEvent(new TaskCreatedEvent(saved));
        return saved;
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public Task update(String id, String name, int position, String columnId,
                       OffsetDateTime dueDate, boolean completed, List<String> tags) {
        columnRepository.findById(columnId)
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        Task existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        Task updated = existing.toBuilder()
                .name(name)
                .position(position)
                .columnId(columnId)
                .dueDate(dueDate)
                .completed(completed)
                .tags(tags)
                .build();
        return repository.save(updated);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void delete(String id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        repository.deleteById(id);
    }
}
