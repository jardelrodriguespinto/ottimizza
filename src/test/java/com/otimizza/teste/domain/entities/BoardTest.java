package com.otimizza.teste.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    @DisplayName("Should create a board with valid data")
    void shouldCreateBoardWithValidData() {
        String id = java.util.UUID.randomUUID().toString();
        String name = "Projeto Kanban";

        Board board = new Board(id, name);

        assertNotNull(board);
        assertEquals(id, board.id());
        assertEquals(name, board.name());
    }
}
