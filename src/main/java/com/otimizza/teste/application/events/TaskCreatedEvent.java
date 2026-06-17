package com.otimizza.teste.application.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.otimizza.teste.domain.entities.Task;
import lombok.Getter;

@Getter
public class TaskCreatedEvent {
    private final Task task;

    @JsonCreator
    public TaskCreatedEvent(@JsonProperty("task") Task task) {
        this.task = task;
    }
}
