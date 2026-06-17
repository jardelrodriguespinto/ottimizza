package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.usecases.BoardUseCase;
import com.otimizza.teste.domain.entities.Board;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Board> create(@Valid @RequestBody CreateBoardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardUseCase.create(request.name()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Board> update(@PathVariable UUID id, @Valid @RequestBody CreateBoardRequest request) {
        return ResponseEntity.ok(boardUseCase.update(id, request.name()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        boardUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    public record CreateBoardRequest(@NotBlank(message = "Name cannot be blank") String name) {}
}
