package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.ColumnDTO;
import com.otimizza.teste.application.dtos.ColumnRequest;
import com.otimizza.teste.application.usecases.ColumnUseCase;
import com.otimizza.teste.domain.entities.Column;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/column")
@RequiredArgsConstructor
public class ColumnController {
    private final ColumnUseCase columnUseCase;

    @GetMapping("/from/{boardId}")
    public ResponseEntity<List<ColumnDTO>> listByBoard(@PathVariable String boardId) {
        List<ColumnDTO> columns = columnUseCase.listByBoard(boardId).stream()
                .map(c -> new ColumnDTO(c.id(), c.name(), c.position(), c.boardId()))
                .toList();
        return ResponseEntity.ok(columns);
    }

    @PostMapping
    public ResponseEntity<ColumnDTO> create(@Valid @RequestBody ColumnRequest request) {
        Column column = columnUseCase.create(request.name(), request.position(), request.boardId());
        return ResponseEntity.ok(new ColumnDTO(column.id(), column.name(), column.position(), column.boardId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColumnDTO> update(@PathVariable String id, @Valid @RequestBody ColumnRequest request) {
        Column column = columnUseCase.update(id, request.name(), request.position(), request.boardId());
        return ResponseEntity.ok(new ColumnDTO(column.id(), column.name(), column.position(), column.boardId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        columnUseCase.delete(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
