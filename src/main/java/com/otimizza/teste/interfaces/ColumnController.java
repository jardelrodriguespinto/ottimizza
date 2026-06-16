package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.usecases.ColumnUseCase;
import com.otimizza.teste.domain.entities.Column;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Column> create(@RequestParam String name, @RequestParam int position, @RequestParam UUID boardId) {
        return ResponseEntity.ok(columnUseCase.create(name, position, boardId));
    }
}
