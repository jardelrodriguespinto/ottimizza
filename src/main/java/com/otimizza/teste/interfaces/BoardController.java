package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.BoardDTO;
import com.otimizza.teste.application.dtos.BoardRequest;
import com.otimizza.teste.application.usecases.BoardUseCase;
import com.otimizza.teste.domain.entities.Board;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardUseCase boardUseCase;

    @GetMapping
    public ResponseEntity<List<BoardDTO>> listAll() {
        List<BoardDTO> boards = boardUseCase.listAll().stream()
                .map(b -> new BoardDTO(b.id(), b.name()))
                .toList();
        return ResponseEntity.ok(boards);
    }

    @PostMapping
    public ResponseEntity<BoardDTO> create(@Valid @RequestBody BoardRequest request) {
        Board board = boardUseCase.create(request.name());
        return ResponseEntity.ok(new BoardDTO(board.id(), board.name()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> update(@PathVariable String id, @Valid @RequestBody BoardRequest request) {
        Board board = boardUseCase.update(id, request.name());
        return ResponseEntity.ok(new BoardDTO(board.id(), board.name()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        boardUseCase.delete(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
