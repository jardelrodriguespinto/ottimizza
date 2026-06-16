package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;

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

    public Board create(String name) {
        Board board = new Board(UUID.randomUUID(), name);
        return repository.save(board);
    }
}
