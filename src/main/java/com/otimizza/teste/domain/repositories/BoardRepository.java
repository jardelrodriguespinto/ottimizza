package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepository {
    List<Board> findAll();
    Optional<Board> findById(String id);
    Board save(Board board);
    void deleteById(String id);
}
