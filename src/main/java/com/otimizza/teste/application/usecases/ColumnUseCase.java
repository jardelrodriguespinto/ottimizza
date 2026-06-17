package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColumnUseCase {
    private final ColumnRepository repository;

    @Cacheable(value = "columns", key = "#boardId")
    public List<Column> listByBoard(UUID boardId) {
        return repository.findByBoardId(boardId);
    }

    public Optional<Column> findById(UUID id) {
        return repository.findById(id);
    }

    @CacheEvict(value = "columns", key = "#boardId")
    public Column create(String name, int position, UUID boardId) {
        Column column = new Column(UUID.randomUUID(), name, position, boardId);
        return repository.save(column);
    }

    @CacheEvict(value = "columns", key = "#boardId")
    public Column update(UUID id, String name, int position, UUID boardId) {
        repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Column not found"));
        return repository.save(new Column(id, name, position, boardId));
    }

    @CacheEvict(value = "columns", key = "#boardId")
    public void delete(UUID id, UUID boardId) {
        repository.deleteById(id);
    }
}
