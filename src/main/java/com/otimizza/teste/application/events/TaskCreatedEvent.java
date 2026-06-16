package com.otimizza.teste.application.events;

import com.otimizza.teste.domain.entities.Task;

public record TaskCreatedEvent(Task task) {}
