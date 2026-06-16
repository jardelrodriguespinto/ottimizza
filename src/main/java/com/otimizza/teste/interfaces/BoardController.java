package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.usecases.BoardUseCase;
import com.otimizza.teste.domain.entities.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardUseCase boardUseCase;

    @GetMapping
    public ResponseEntity<List<Board>> listAll() {
        return ResponseEntity.ok(boardUseCase.listAll());
    }

    @PostMapping
    public ResponseEntity<Board> create(@RequestParam String name) {
        return ResponseEntity.ok(boardUseCase.create(name));
    }
}
