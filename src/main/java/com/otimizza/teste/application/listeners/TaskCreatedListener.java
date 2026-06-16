package com.otimizza.teste.application.listeners;

import com.otimizza.teste.application.events.TaskCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TaskCreatedListener {

    @EventListener
    public void handleTaskCreated(TaskCreatedEvent event) {
        System.out.println("New task created: " + event.task().name());
    }
}
