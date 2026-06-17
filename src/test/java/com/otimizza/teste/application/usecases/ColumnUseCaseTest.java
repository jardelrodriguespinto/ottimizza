package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.repositories.ColumnRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ColumnUseCaseTest {

    @Mock
    private ColumnRepository repository;

    @InjectMocks
    private ColumnUseCase columnUseCase;

    @Test
    @DisplayName("Should list columns by board ID")
    void shouldListColumnsByBoard() {
        String boardId = java.util.UUID.randomUUID().toString();
        Column column = new Column(java.util.UUID.randomUUID().toString(), "To Do", 1, boardId);
        when(repository.findByBoardId(java.util.UUID.fromString(boardId))).thenReturn(List.of(column));

        List<Column> columns = columnUseCase.listByBoard(boardId);

        assertFalse(columns.isEmpty());
        assertEquals(1, columns.size());
        verify(repository, times(1)).findByBoardId(java.util.UUID.fromString(boardId));
    }

    @Test
    @DisplayName("Should create a column")
    void shouldCreateColumn() {
        String boardId = java.util.UUID.randomUUID().toString();
        String name = "In Progress";
        int position = 2;
        Column column = new Column(java.util.UUID.randomUUID().toString(), name, position, boardId);
        when(repository.save(any(Column.class))).thenReturn(column);

        Column createdColumn = columnUseCase.create(name, position, boardId);

        assertNotNull(createdColumn);
        assertEquals(name, createdColumn.name());
        verify(repository, times(1)).save(any(Column.class));
    }
}
