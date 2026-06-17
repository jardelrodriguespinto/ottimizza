package com.otimizza.teste.infrastructure.persistence;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.repositories.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaBoardRepositoryImpl.class)
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("Should persist and retrieve a board")
    void shouldPersistAndRetrieveBoard() {
        String id = UUID.randomUUID().toString();
        Board board = new Board(id, "Kanban Board");

        boardRepository.save(board);
        Optional<Board> found = boardRepository.findById(id);

        assertTrue(found.isPresent());
        assertEquals("Kanban Board", found.get().name());
        assertEquals(id, found.get().id());
    }

    @Test
    @DisplayName("Should return all boards paginated")
    void shouldReturnAllBoards() {
        boardRepository.save(new Board(UUID.randomUUID().toString(), "Board A"));
        boardRepository.save(new Board(UUID.randomUUID().toString(), "Board B"));

        Page<Board> boards = boardRepository.findAll(PageRequest.of(0, 10));

        assertTrue(boards.getTotalElements() >= 2);
    }

    @Test
    @DisplayName("Should delete a board by id")
    void shouldDeleteBoardById() {
        String id = UUID.randomUUID().toString();
        boardRepository.save(new Board(id, "To Delete"));

        boardRepository.deleteById(id);

        assertTrue(boardRepository.findById(id).isEmpty());
    }

    @Test
    @DisplayName("Should return empty when board not found")
    void shouldReturnEmptyWhenNotFound() {
        Optional<Board> result = boardRepository.findById(UUID.randomUUID().toString());
        assertTrue(result.isEmpty());
    }
}
