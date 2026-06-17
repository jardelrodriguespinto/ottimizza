package com.otimizza.teste.interfaces;

import com.otimizza.teste.application.dtos.TaskDTO;
import com.otimizza.teste.application.dtos.TaskRequest;
import com.otimizza.teste.application.usecases.TaskUseCase;
import com.otimizza.teste.domain.entities.Task;
import com.otimizza.teste.interfaces.mappers.TaskMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final TaskMapper mapper;

    @GetMapping("/from/{columnId}")
    public ResponseEntity<List<TaskDTO>> listByColumn(@PathVariable String columnId) {
        List<TaskDTO> tasks = taskUseCase.listByColumn(columnId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody TaskRequest request) {
        Task task = taskUseCase.create(request);
        return ResponseEntity.ok(mapper.toDTO(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable String id, @Valid @RequestBody TaskRequest request) {
        Task task = taskUseCase.update(id, request);
        return ResponseEntity.ok(mapper.toDTO(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable String id) {
        taskUseCase.delete(id);
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
