package com.otimizza.teste.infrastructure.persistence;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.repositories.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

interface SpringDataBoardRepository extends JpaRepository<BoardEntity, UUID> {}

@Repository
@RequiredArgsConstructor
public class JpaBoardRepositoryImpl implements BoardRepository {
    private final SpringDataBoardRepository repository;

    @Override
    public List<Board> findAll() {
        return repository.findAll().stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Board> findById(String id) {
        return repository.findById(UUID.fromString(id)).map(this::toDomain);
    }

    @Override
    public Board save(Board board) {
        repository.save(new BoardEntity(UUID.fromString(board.id()), board.name()));
        return board;
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(UUID.fromString(id));
    }

    private Board toDomain(BoardEntity e) {
        return new Board(e.getId().toString(), e.getName());
    }
}
