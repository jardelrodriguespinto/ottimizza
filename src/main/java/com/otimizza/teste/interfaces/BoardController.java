package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.BoardDTO;
import com.otimizza.teste.application.usecases.BoardUseCase;
import com.otimizza.teste.domain.entities.Board;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

    @GetMapping
    public ResponseEntity<List<BoardDTO>> listAll() {
        List<BoardDTO> boards = boardUseCase.listAll().stream()
                .map(b -> new BoardDTO(b.id().toString(), b.name()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(boards);
    }

    @PostMapping
    public ResponseEntity<BoardDTO> create(@Valid @RequestBody CreateBoardRequest request) {
        Board board = boardUseCase.create(request.name());
        return ResponseEntity.ok(new BoardDTO(board.id().toString(), board.name()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> update(@PathVariable String id, @Valid @RequestBody CreateBoardRequest request) {
        Board board = boardUseCase.update(id, request.name());
        return ResponseEntity.ok(new BoardDTO(board.id().toString(), board.name()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        boardUseCase.delete(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    public record CreateBoardRequest(@NotBlank(message = "Name cannot be blank") String name) {}
}
