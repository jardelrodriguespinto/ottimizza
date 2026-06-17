package com.otimizza.teste.application.usecases;

import com.otimizza.teste.application.dtos.TaskRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskUseCase {
    private final TaskRepository repository;
    private final ColumnRepository columnRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Cacheable(value = "tasks", key = "{#columnId, #pageable.pageNumber, #pageable.pageSize}")
    public Page<Task> listByColumn(String columnId, Pageable pageable) {
        return repository.findByColumnId(columnId, pageable);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public Task create(TaskRequest request) {
        columnRepository.findById(request.columnId())
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        Task task = DomainFactory.createTask(request);
        Task saved = repository.save(task);
        eventPublisher.publishEvent(new TaskCreatedEvent(saved));
        return saved;
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public Task update(String id, TaskRequest request) {
        columnRepository.findById(request.columnId())
                .orElseThrow(() -> new EntityNotFoundException("Column not found"));
        Task existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        Task updated = existing.toBuilder()
                .name(request.name())
                .position(request.position())
                .columnId(request.columnId())
                .dueDate(request.dueDate())
                .completed(request.completed())
                .tags(request.tags())
                .build();
        return repository.save(updated);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void delete(String id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        repository.deleteById(id);
    }
}
