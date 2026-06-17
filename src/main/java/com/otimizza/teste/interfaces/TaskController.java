package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.TaskDTO;
import com.otimizza.teste.application.usecases.TaskUseCase;
import com.otimizza.teste.domain.entities.Task;
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
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskUseCase taskUseCase;

    @GetMapping("/from/{columnId}")
    public ResponseEntity<List<TaskDTO>> listByColumn(@PathVariable String columnId) {
        List<TaskDTO> tasks = taskUseCase.listByColumn(columnId).stream()
                .map(t -> new TaskDTO(t.id().toString(), t.name(), t.position(), t.createdAt(), t.dueDate(), t.completed(), t.tags(), t.columnId().toString()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody CreateTaskRequest request) {
        Task task = taskUseCase.create(request.name(), request.position(), request.columnId());
        return ResponseEntity.ok(new TaskDTO(task.id().toString(), task.name(), task.position(), task.createdAt(), task.dueDate(), task.completed(), task.tags(), task.columnId().toString()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable String id, @Valid @RequestBody UpdateTaskRequest request) {
        Task task = taskUseCase.update(id, request.name(), request.position(), request.columnId(), request.completed(), request.tags());
        return ResponseEntity.ok(new TaskDTO(task.id().toString(), task.name(), task.position(), task.createdAt(), task.dueDate(), task.completed(), task.tags(), task.columnId().toString()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        Task task = taskUseCase.findById(id).orElseThrow();
        taskUseCase.delete(id, task.columnId());
        return ResponseEntity.ok(Map.of("status", "ok"));
    }

    public record CreateTaskRequest(
            @NotBlank(message = "Name cannot be blank") String name,
            int position,
            @NotNull(message = "Column ID cannot be null") String columnId) {}

    public record UpdateTaskRequest(
            @NotBlank(message = "Name cannot be blank") String name,
            int position,
            @NotNull(message = "Column ID cannot be null") String columnId,
            boolean completed,
            List<String> tags) {}
}
