package com.otimizza.teste.infrastructure.messaging;

import com.otimizza.teste.application.events.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMessageProducer {

    private final RabbitTemplate rabbitTemplate;
    public static final String EXCHANGE = "task.exchange";
    public static final String ROUTING_KEY = "task.created";

    public void sendTaskCreatedMessage(TaskCreatedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);
    }
}
