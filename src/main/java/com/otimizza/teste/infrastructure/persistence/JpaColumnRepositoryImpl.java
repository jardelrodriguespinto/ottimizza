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
    public List<Column> findByBoardId(String boardId) {
        return repository.findByBoardId(UUID.fromString(boardId))
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Column> findById(String id) {
        return repository.findById(UUID.fromString(id)).map(this::toDomain);
    }

    @Override
    public Column save(Column column) {
        repository.save(new ColumnEntity(
                UUID.fromString(column.id()),
                column.name(),
                column.position(),
                UUID.fromString(column.boardId())));
        return column;
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    private Column toDomain(ColumnEntity e) {
        return new Column(e.getId().toString(), e.getName(), e.getPosition(), e.getBoardId().toString());
    }
}
