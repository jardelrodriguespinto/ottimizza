package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.repositories.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
        Board board = new Board(UUID.randomUUID(), "Board 1");
        when(repository.findAll()).thenReturn(List.of(board));

        List<Board> boards = boardUseCase.listAll();

        assertFalse(boards.isEmpty());
        assertEquals(1, boards.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should create a board")
    void shouldCreateBoard() {
        String name = "New Board";
        Board board = new Board(UUID.randomUUID(), name);
        when(repository.save(any(Board.class))).thenReturn(board);

        Board createdBoard = boardUseCase.create(name);

        assertNotNull(createdBoard);
        assertEquals(name, createdBoard.name());
        verify(repository, times(1)).save(any(Board.class));
    }
}
