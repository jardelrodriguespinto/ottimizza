package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.domain.repositories.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardUseCaseTest {

    @Mock
    private BoardRepository repository;

    @InjectMocks
    private BoardUseCase boardUseCase;

    @Test
    @DisplayName("Should list all boards")
    void shouldListAllBoards() {
        Board board = new Board(UUID.randomUUID().toString(), "Board 1");
        when(repository.findAll()).thenReturn(List.of(board));

        List<Board> boards = boardUseCase.listAll();

        assertFalse(boards.isEmpty());
        assertEquals(1, boards.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Should create a board")
    void shouldCreateBoard() {
        String name = "New Board";
        when(repository.save(any(Board.class))).thenAnswer(inv -> inv.getArgument(0));

        Board created = boardUseCase.create(name);

        assertNotNull(created);
        assertEquals(name, created.name());
        verify(repository).save(any(Board.class));
    }

    @Test
    @DisplayName("Should update a board")
    void shouldUpdateBoard() {
        String id = UUID.randomUUID().toString();
        Board existing = new Board(id, "Old Name");
        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Board.class))).thenAnswer(inv -> inv.getArgument(0));

        Board updated = boardUseCase.update(id, "New Name");

        assertEquals("New Name", updated.name());
        verify(repository).save(any(Board.class));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when board not found on update")
    void shouldThrowWhenBoardNotFoundOnUpdate() {
        String id = UUID.randomUUID().toString();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> boardUseCase.update(id, "Name"));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when board not found on delete")
    void shouldThrowWhenBoardNotFoundOnDelete() {
        String id = UUID.randomUUID().toString();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> boardUseCase.delete(id));
    }
}
