package com.otimizza.teste.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ColumnTest {

    @Test
    @DisplayName("Should create a column with valid data")
    void shouldCreateColumnWithValidData() {
        String id = UUID.randomUUID().toString();
        String boardId = UUID.randomUUID().toString();

        Column column = new Column(id, "A Fazer", 0, boardId);

        assertEquals(id, column.id());
        assertEquals("A Fazer", column.name());
        assertEquals(0, column.position());
        assertEquals(boardId, column.boardId());
    }

    @Test
    @DisplayName("Should throw when name is blank")
    void shouldThrowWhenNameIsBlank() {
        assertThrows(IllegalArgumentException.class,
                () -> new Column(UUID.randomUUID().toString(), "", 0, UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("Should generate id when null")
    void shouldGenerateIdWhenNull() {
        Column column = new Column(null, "A Fazer", 0, UUID.randomUUID().toString());
        assertNotNull(column.id());
    }

    @Test
    @DisplayName("Should throw when boardId is null")
    void shouldThrowWhenBoardIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Column(UUID.randomUUID().toString(), "A Fazer", 0, null));
    }
}
