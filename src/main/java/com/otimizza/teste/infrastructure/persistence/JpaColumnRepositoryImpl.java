package com.otimizza.teste.infrastructure.persistence;

import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

interface SpringDataColumnRepository extends JpaRepository<ColumnEntity, UUID> {
    List<ColumnEntity> findByBoardId(UUID boardId);
}

@Repository
@RequiredArgsConstructor
public class JpaColumnRepositoryImpl implements ColumnRepository {
    private final SpringDataColumnRepository repository;

    @Override
    public List<Column> findByBoardId(UUID boardId) {
        return repository.findByBoardId(boardId).stream()
                .map(e -> new Column(e.getId().toString(), e.getName(), e.getPosition(), e.getBoardId().toString()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Column> findById(UUID id) {
        return repository.findById(id)
                .map(e -> new Column(e.getId().toString(), e.getName(), e.getPosition(), e.getBoardId().toString()));
    }

    @Override
    public Column save(Column column) {
        ColumnEntity entity = new ColumnEntity(java.util.UUID.fromString(column.id()), column.name(), column.position(), java.util.UUID.fromString(column.boardId()));
        repository.save(entity);
        return column;
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
