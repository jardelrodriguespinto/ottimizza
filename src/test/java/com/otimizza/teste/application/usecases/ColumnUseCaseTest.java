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
import java.util.UUID;

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
        UUID boardId = UUID.randomUUID();
        Column column = new Column(UUID.randomUUID(), "To Do", 1, boardId);
        when(repository.findByBoardId(boardId)).thenReturn(List.of(column));

        List<Column> columns = columnUseCase.listByBoard(boardId);

        assertFalse(columns.isEmpty());
        assertEquals(1, columns.size());
        verify(repository, times(1)).findByBoardId(boardId);
    }

    @Test
    @DisplayName("Should create a column")
    void shouldCreateColumn() {
        UUID boardId = UUID.randomUUID();
        String name = "In Progress";
        int position = 2;
        Column column = new Column(UUID.randomUUID(), name, position, boardId);
        when(repository.save(any(Column.class))).thenReturn(column);

        Column createdColumn = columnUseCase.create(name, position, boardId);

        assertNotNull(createdColumn);
        assertEquals(name, createdColumn.name());
        verify(repository, times(1)).save(any(Column.class));
    }
}
