package com.otimizza.teste.application.usecases;

import com.otimizza.teste.application.dtos.ColumnRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    @DisplayName("Should list columns by board ID with pagination")
    void shouldListColumnsByBoard() {
        String boardId = UUID.randomUUID().toString();
        Column column = new Column(UUID.randomUUID().toString(), "To Do", 0, boardId);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Column> page = new PageImpl<>(List.of(column), pageable, 1);
        
        when(repository.findByBoardId(boardId, pageable)).thenReturn(page);

        Page<Column> result = columnUseCase.listByBoard(boardId, pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        verify(repository).findByBoardId(boardId, pageable);
    }

    @Test
    @DisplayName("Should create a column")
    void shouldCreateColumn() {
        String boardId = UUID.randomUUID().toString();
        ColumnRequest request = new ColumnRequest("In Progress", 1, boardId);
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(new Board(boardId, "Board")));
        when(repository.save(any(Column.class))).thenAnswer(inv -> inv.getArgument(0));

        Column created = columnUseCase.create(request);

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
        ColumnRequest request = new ColumnRequest("Name", 0, boardId);
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(new Board(boardId, "Board")));
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> columnUseCase.update(id, request));
    }
}
