package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.domain.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        return repository.save(new Board(UUID.randomUUID().toString(), name));
    }

    @CacheEvict(value = "boards", allEntries = true)
    public Board update(String id, String name) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Board not found"));
        return repository.save(new Board(id, name));
    }

    @CacheEvict(value = "boards", allEntries = true)
    public void delete(String id) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Board not found"));
        repository.deleteById(id);
    }
}
