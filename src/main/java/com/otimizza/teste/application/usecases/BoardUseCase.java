package com.otimizza.teste.application.usecases;

import com.otimizza.teste.application.dtos.BoardRequest;
import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.domain.factories.DomainFactory;
import com.otimizza.teste.domain.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardUseCase {
    private final BoardRepository repository;

    @Cacheable(value = "boards", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public Page<Board> listAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @CacheEvict(value = "boards", allEntries = true)
    public Board create(BoardRequest request) {
        return repository.save(DomainFactory.createBoard(request));
    }

    @CacheEvict(value = "boards", allEntries = true)
    public Board update(String id, BoardRequest request) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Board not found"));
        return repository.save(new Board(id, request.name()));
    }

    @CacheEvict(value = "boards", allEntries = true)
    public void delete(String id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Board not found"));
        repository.deleteById(id);
    }
}
