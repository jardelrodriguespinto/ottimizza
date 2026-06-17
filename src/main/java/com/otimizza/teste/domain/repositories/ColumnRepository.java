package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Column;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ColumnRepository {
    Page<Column> findByBoardId(String boardId, Pageable pageable);
    Optional<Column> findById(String id);
    Column save(Column column);
    void deleteById(String id);
}
