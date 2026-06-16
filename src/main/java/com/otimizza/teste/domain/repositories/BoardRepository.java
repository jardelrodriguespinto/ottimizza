package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Board;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepository {
    List<Board> findAll();
    Optional<Board> findById(UUID id);
    Board save(Board board);
    void deleteById(UUID id);
}
