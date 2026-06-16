package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.usecases.TaskUseCase;
import com.otimizza.teste.domain.entities.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskUseCase taskUseCase;

    @GetMapping("/from/{columnId}")
    public ResponseEntity<List<Task>> listByColumn(@PathVariable UUID columnId) {
        return ResponseEntity.ok(taskUseCase.listByColumn(columnId));
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestParam String name, @RequestParam int position, @RequestParam UUID columnId) {
        return ResponseEntity.ok(taskUseCase.create(name, position, columnId));
    }
}
