package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Column;

import java.util.List;
import java.util.Optional;

public interface ColumnRepository {
    List<Column> findByBoardId(String boardId);
    Optional<Column> findById(String id);
    Column save(Column column);
    void deleteById(String id);
}
