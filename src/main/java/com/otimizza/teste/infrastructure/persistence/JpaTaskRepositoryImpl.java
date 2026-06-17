package com.otimizza.teste.infrastructure.persistence;

import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.domain.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

interface SpringDataTaskRepository extends JpaRepository<TaskEntity, UUID> {
    Page<TaskEntity> findByColumnId(UUID columnId, Pageable pageable);
}

@Repository
@RequiredArgsConstructor
public class JpaTaskRepositoryImpl implements TaskRepository {
    private final SpringDataTaskRepository repository;

    @Override
    public Page<Task> findByColumnId(String columnId, Pageable pageable) {
        return repository.findByColumnId(UUID.fromString(columnId), pageable)
                .map(this::toDomain);
    }

    @Override
    public Optional<Task> findById(String id) {
        return repository.findById(UUID.fromString(id)).map(this::toDomain);
    }

    @Override
    public Task save(Task task) {
        repository.save(new TaskEntity(
                UUID.fromString(task.id()),
                task.name(),
                task.position(),
                task.createdAt(),
                task.dueDate(),
                task.completed(),
                task.tags(),
                UUID.fromString(task.columnId())));
        return task;
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    private Task toDomain(TaskEntity e) {
        return new Task(
                e.getId().toString(),
                e.getName(),
                e.getPosition(),
                e.getCreatedAt(),
                e.getDueDate(),
                e.isCompleted(),
                e.getTags(),
                e.getColumnId().toString());
    }
}
