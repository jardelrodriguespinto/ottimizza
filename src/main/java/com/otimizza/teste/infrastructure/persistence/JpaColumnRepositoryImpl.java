package com.otimizza.teste.infrastructure.persistence;

import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

interface SpringDataColumnRepository extends JpaRepository<ColumnEntity, UUID> {
    Page<ColumnEntity> findByBoardId(UUID boardId, Pageable pageable);
}

@Repository
@RequiredArgsConstructor
public class JpaColumnRepositoryImpl implements ColumnRepository {
    private final SpringDataColumnRepository repository;

    @Override
    public Page<Column> findByBoardId(String boardId, Pageable pageable) {
        return repository.findByBoardId(UUID.fromString(boardId), pageable)
                .map(this::toDomain);
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
