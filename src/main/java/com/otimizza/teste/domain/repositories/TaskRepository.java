package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskRepository {
    Page<Task> findByColumnId(String columnId, Pageable pageable);
    Optional<Task> findById(String id);
    Task save(Task task);
    void deleteById(String id);
}
