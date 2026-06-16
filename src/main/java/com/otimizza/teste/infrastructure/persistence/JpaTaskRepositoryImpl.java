package com.otimizza.teste.infrastructure.persistence;

import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Arrays;

interface SpringDataTaskRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findByColumnId(UUID columnId);
}

@Repository
@RequiredArgsConstructor
public class JpaTaskRepositoryImpl implements TaskRepository {
    private final SpringDataTaskRepository repository;

    @Override
    public List<Task> findByColumnId(UUID columnId) {
        return repository.findByColumnId(columnId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = new TaskEntity(task.id(), task.name(), task.position(), 
                                           task.createdAt(), task.dueDate(), task.completed(), 
                                           String.join(",", task.tags()), task.columnId());
        repository.save(entity);
        return task;
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private Task toDomain(TaskEntity e) {
        return new Task(e.getId(), e.getName(), e.getPosition(), e.getCreatedAt(), 
                        e.getDueDate(), e.isCompleted(), 
                        e.getTags() != null && !e.getTags().isEmpty() ? Arrays.asList(e.getTags().split(",")) : List.of(), e.getColumnId());
    }
}
