package com.otimizza.teste.application.usecases;

import com.otimizza.teste.application.dtos.ColumnRequest;
import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.domain.factories.DomainFactory;
import com.otimizza.teste.domain.repositories.BoardRepository;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnUseCase {
    private final ColumnRepository repository;
    private final BoardRepository boardRepository;

    @Cacheable(value = "columns", key = "{#boardId, #pageable.pageNumber, #pageable.pageSize}")
    public Page<Column> listByBoard(String boardId, Pageable pageable) {
        return repository.findByBoardId(boardId, pageable);
    }

    @CacheEvict(value = "columns", allEntries = true)
    public Column create(ColumnRequest request) {
        boardRepository.findById(request.boardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        return repository.save(DomainFactory.createColumn(request));
    }

    @CacheEvict(value = "columns", allEntries = true)
    public Column update(String id, ColumnRequest request) {
        boardRepository.findById(request.boardId())
                .orElseThrow(() -> new EntityNotFoundException("Board not found"));
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Column not found"));
        return repository.save(new Column(id, request.name(), request.position(), request.boardId()));
    }

    @CacheEvict(value = "columns", allEntries = true)
    public void delete(String id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Column not found"));
        repository.deleteById(id);
    }
}
