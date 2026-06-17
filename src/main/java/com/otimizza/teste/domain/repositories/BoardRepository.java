package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BoardRepository {
    Page<Board> findAll(Pageable pageable);
    Optional<Board> findById(String id);
    Board save(Board board);
    void deleteById(String id);
}
