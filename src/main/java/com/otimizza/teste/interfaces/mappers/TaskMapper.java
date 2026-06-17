package com.otimizza.teste.interfaces.mappers;

import com.otimizza.teste.application.dtos.TaskDTO;
import com.otimizza.teste.domain.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.id(),
                task.name(),
                task.position(),
                task.createdAt(),
                task.dueDate(),
                task.completed(),
                task.tags(),
                task.columnId());
    }
}
