package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.ColumnDTO;
import com.otimizza.teste.application.dtos.ColumnRequest;
import com.otimizza.teste.application.usecases.ColumnUseCase;
import com.otimizza.teste.domain.entities.Column;
import com.otimizza.teste.interfaces.mappers.ColumnMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/column")
@RequiredArgsConstructor
public class ColumnController {
    private final ColumnUseCase columnUseCase;
    private final ColumnMapper mapper;

    @GetMapping("/from/{boardId}")
    public ResponseEntity<Page<ColumnDTO>> listByBoard(@PathVariable String boardId, Pageable pageable) {
        Page<ColumnDTO> columns = columnUseCase.listByBoard(boardId, pageable)
                .map(mapper::toDTO);
        return ResponseEntity.ok(columns);
    }

    @PostMapping
    public ResponseEntity<ColumnDTO> create(@Valid @RequestBody ColumnRequest request) {
        Column column = columnUseCase.create(request);
        return ResponseEntity.ok(mapper.toDTO(column));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColumnDTO> update(@PathVariable String id, @Valid @RequestBody ColumnRequest request) {
        Column column = columnUseCase.update(id, request);
        return ResponseEntity.ok(mapper.toDTO(column));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        columnUseCase.delete(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
