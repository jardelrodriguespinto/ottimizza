package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.ColumnDTO;
import com.otimizza.teste.application.usecases.ColumnUseCase;
import com.otimizza.teste.domain.entities.Column;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/column")
@RequiredArgsConstructor
public class ColumnController {
    private final ColumnUseCase columnUseCase;

    @GetMapping("/from/{boardId}")
    public ResponseEntity<List<ColumnDTO>> listByBoard(@PathVariable String boardId) {
        List<ColumnDTO> columns = columnUseCase.listByBoard(boardId).stream()
                .map(c -> new ColumnDTO(c.id().toString(), c.name(), c.position(), c.boardId().toString()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(columns);
    }

    @PostMapping
    public ResponseEntity<ColumnDTO> create(@Valid @RequestBody CreateColumnRequest request) {
        Column column = columnUseCase.create(request.name(), request.position(), request.boardId());
        return ResponseEntity.ok(new ColumnDTO(column.id().toString(), column.name(), column.position(), column.boardId().toString()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColumnDTO> update(@PathVariable String id, @Valid @RequestBody CreateColumnRequest request) {
        Column column = columnUseCase.update(id, request.name(), request.position(), request.boardId());
        return ResponseEntity.ok(new ColumnDTO(column.id().toString(), column.name(), column.position(), column.boardId().toString()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        Column column = columnUseCase.findById(id).orElseThrow();
        columnUseCase.delete(id, column.boardId());
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    public record CreateColumnRequest(
            @NotBlank(message = "Name cannot be blank") String name,
            int position,
            @NotNull(message = "Board ID cannot be null") String boardId) {}
}
