package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ColumnUseCase {
    private final ColumnRepository repository;

    @Cacheable(value = "columns", key = "#boardId")
    public List<Column> listByBoard(String boardId) {
        return repository.findByBoardId(java.util.UUID.fromString(boardId));
    }

    public Optional<Column> findById(String id) {
        return repository.findById(java.util.UUID.fromString(id));
    }

    @CacheEvict(value = "columns", key = "#boardId")
    public Column create(String name, int position, String boardId) {
        Column column = new Column(java.util.UUID.randomUUID().toString(), name, position, boardId);
        return repository.save(column);
    }

    @CacheEvict(value = "columns", key = "#boardId")
    public Column update(String id, String name, int position, String boardId) {
        repository.findById(java.util.UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("Column not found"));
        return repository.save(new Column(id, name, position, boardId));
    }

    @CacheEvict(value = "columns", key = "#boardId")
    public void delete(String id, String boardId) {
        repository.deleteById(java.util.UUID.fromString(id));
    }
}
