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
        return repository.findAll().stream()
                .map(e -> new Board(e.getId(), e.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Board> findById(UUID id) {
        return repository.findById(id)
                .map(e -> new Board(e.getId(), e.getName()));
    }

    @Override
    public Board save(Board board) {
        BoardEntity entity = new BoardEntity(board.id(), board.name());
        repository.save(entity);
        return board;
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
