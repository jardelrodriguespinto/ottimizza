package com.otimizza.teste.application.listeners;

import com.otimizza.teste.application.events.TaskCreatedEvent;
import com.otimizza.teste.infrastructure.messaging.TaskMessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskCreatedListener {

    private final TaskMessageProducer messageProducer;

    @EventListener
    public void handleTaskCreated(TaskCreatedEvent event) {
        log.info("New task created: {}. Sending to RabbitMQ...", event.getTask().name());
        messageProducer.sendTaskCreatedMessage(event);
    }
}
