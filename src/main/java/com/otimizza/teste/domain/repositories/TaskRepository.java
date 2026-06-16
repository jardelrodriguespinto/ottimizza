package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Task;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    List<Task> findByColumnId(UUID columnId);
    Optional<Task> findById(UUID id);
    Task save(Task task);
    void deleteById(UUID id);
}
