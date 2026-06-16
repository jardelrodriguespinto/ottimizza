package com.otimizza.teste.domain.repositories;

import com.otimizza.teste.domain.entities.Column;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColumnRepository {
    List<Column> findByBoardId(UUID boardId);
    Optional<Column> findById(UUID id);
    Column save(Column column);
    void deleteById(UUID id);
}
