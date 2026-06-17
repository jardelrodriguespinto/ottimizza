package com.otimizza.teste.application.usecases;

import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.domain.exceptions.EntityNotFoundException;
import com.otimizza.teste.domain.repositories.BoardRepository;
import com.otimizza.teste.domain.repositories.ColumnRepository;
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
class ColumnUseCaseTest {

    @Mock
    private ColumnRepository repository;

    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private ColumnUseCase columnUseCase;

    @Test
    @DisplayName("Should list columns by board ID")
    void shouldListColumnsByBoard() {
        String boardId = UUID.randomUUID().toString();
        Column column = new Column(UUID.randomUUID().toString(), "To Do", 0, boardId);
        when(repository.findByBoardId(boardId)).thenReturn(List.of(column));

        List<Column> columns = columnUseCase.listByBoard(boardId);

        assertFalse(columns.isEmpty());
        assertEquals(1, columns.size());
        verify(repository).findByBoardId(boardId);
    }

    @Test
    @DisplayName("Should create a column")
    void shouldCreateColumn() {
        String boardId = UUID.randomUUID().toString();
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(new Board(boardId, "Board")));
        when(repository.save(any(Column.class))).thenAnswer(inv -> inv.getArgument(0));

        Column created = columnUseCase.create("In Progress", 1, boardId);

        assertNotNull(created);
        assertEquals("In Progress", created.name());
        verify(repository).save(any(Column.class));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when column not found on delete")
    void shouldThrowWhenColumnNotFoundOnDelete() {
        String id = UUID.randomUUID().toString();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> columnUseCase.delete(id));
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when column not found on update")
    void shouldThrowWhenColumnNotFoundOnUpdate() {
        String id = UUID.randomUUID().toString();
        String boardId = UUID.randomUUID().toString();
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(new Board(boardId, "Board")));
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> columnUseCase.update(id, "Name", 0, boardId));
    }
}
