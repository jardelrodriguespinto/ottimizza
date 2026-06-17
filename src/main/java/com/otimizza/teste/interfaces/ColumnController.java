package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.usecases.ColumnUseCase;
import com.otimizza.teste.domain.entities.Column;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/column")
@RequiredArgsConstructor
public class ColumnController {
    private final ColumnUseCase columnUseCase;

    @GetMapping("/from/{boardId}")
    public ResponseEntity<List<Column>> listByBoard(@PathVariable UUID boardId) {
        return ResponseEntity.ok(columnUseCase.listByBoard(boardId));
    }

    @PostMapping
    public ResponseEntity<Column> create(@Valid @RequestBody CreateColumnRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(columnUseCase.create(request.name(), request.position(), request.boardId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Column> update(@PathVariable UUID id, @Valid @RequestBody CreateColumnRequest request) {
        return ResponseEntity.ok(columnUseCase.update(id, request.name(), request.position(), request.boardId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Column column = columnUseCase.listByBoard(columnUseCase.findById(id).orElseThrow().boardId()).stream()
            .filter(c -> c.id().equals(id)).findFirst().orElseThrow();
        columnUseCase.delete(id, column.boardId());
        return ResponseEntity.noContent().build();
    }

    public record CreateColumnRequest(
            @NotBlank(message = "Name cannot be blank") String name,
            int position,
            @NotNull(message = "Board ID cannot be null") UUID boardId) {}
}
