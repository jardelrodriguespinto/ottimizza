package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findByColumnId(String columnId);
    Optional<Task> findById(String id);
    Task save(Task task);
    void deleteById(String id);
}
