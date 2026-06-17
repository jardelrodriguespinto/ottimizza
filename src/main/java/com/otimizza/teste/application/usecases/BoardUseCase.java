package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardUseCase {
    private final BoardRepository repository;

    @Cacheable("boards")
    public List<Board> listAll() {
        return repository.findAll();
    }

    @CacheEvict(value = "boards", allEntries = true)
    public Board create(String name) {
        Board board = new Board(java.util.UUID.randomUUID().toString(), name);
        return repository.save(board);
    }

    @CacheEvict(value = "boards", allEntries = true)
    public Board update(String id, String name) {
        repository.findById(java.util.UUID.fromString(id)).orElseThrow(() -> new IllegalArgumentException("Board not found"));
        return repository.save(new Board(id, name));
    }

    @CacheEvict(value = "boards", allEntries = true)
    public void delete(String id) {
        repository.deleteById(java.util.UUID.fromString(id));
    }
}
