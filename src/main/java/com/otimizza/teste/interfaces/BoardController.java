package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.BoardDTO;
import com.otimizza.teste.application.dtos.BoardRequest;
import com.otimizza.teste.application.usecases.BoardUseCase;
import com.otimizza.teste.domain.entities.Board;
import com.otimizza.teste.interfaces.mappers.BoardMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardUseCase boardUseCase;
    private final BoardMapper mapper;

    @GetMapping
    public ResponseEntity<List<BoardDTO>> listAll() {
        List<BoardDTO> boards = boardUseCase.listAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(boards);
    }

    @PostMapping
    public ResponseEntity<BoardDTO> create(@Valid @RequestBody BoardRequest request) {
        Board board = boardUseCase.create(request);
        return ResponseEntity.ok(mapper.toDTO(board));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> update(@PathVariable String id, @Valid @RequestBody BoardRequest request) {
        Board board = boardUseCase.update(id, request);
        return ResponseEntity.ok(mapper.toDTO(board));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        boardUseCase.delete(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
