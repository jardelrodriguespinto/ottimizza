package com.otimizza.teste.application.listeners;

import lombok.extern.slf4j.Slf4j;
import com.otimizza.teste.application.events.TaskCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskCreatedListener {

    @EventListener
    public void handleTaskCreated(TaskCreatedEvent event) {
        log.info("New task created: {}", event.task().name());
    }
}
