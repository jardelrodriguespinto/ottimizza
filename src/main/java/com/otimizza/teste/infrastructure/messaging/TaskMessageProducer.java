package com.otimizza.teste.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otimizza.teste.application.events.TaskCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMessageProducer {

    @Qualifier("rabbitTemplate")
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    public static final String EXCHANGE = "task.exchange";
    public static final String ROUTING_KEY = "task.created";

    @SneakyThrows
    public void sendTaskCreatedMessage(TaskCreatedEvent event) {
        String json = objectMapper.writeValueAsString(event);
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, json);
    }
}
