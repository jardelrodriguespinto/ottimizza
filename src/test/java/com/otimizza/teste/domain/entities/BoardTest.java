package com.otimizza.teste.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    @DisplayName("Should create a board with valid data")
    void shouldCreateBoardWithValidData() {
        UUID id = UUID.randomUUID();
        String name = "Projeto Kanban";

        Board board = new Board(id, name);

        assertNotNull(board);
        assertEquals(id, board.getId());
        assertEquals(name, board.getName());
    }
}
