package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.domain.repositories.BoardRepository;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ColumnUseCase {
    private final ColumnRepository repository;
    private final BoardRepository boardRepository;

    @Cacheable(value = "columns", key = "#boardId")
    public List<Column> listByBoard(String boardId) {
        return repository.findByBoardId(boardId);
    }

    @CacheEvict(value = "columns", key = "#boardId")
    public Column create(String name, int position, String boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        return repository.save(new Column(UUID.randomUUID().toString(), name, position, boardId));
    }

    @CacheEvict(value = "columns", key = "#boardId")
    public Column update(String id, String name, int position, String boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Column not found"));
        return repository.save(new Column(id, name, position, boardId));
    }

    @CacheEvict(value = "columns", allEntries = true)
    public void delete(String id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Column not found"));
        repository.deleteById(id);
    }
}
