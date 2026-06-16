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

    public record CreateBoardRequest(@NotBlank(message = "Name cannot be blank") String name) {}
}
