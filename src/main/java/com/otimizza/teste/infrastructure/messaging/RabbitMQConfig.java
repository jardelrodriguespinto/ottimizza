package com.otimizza.teste.infrastructure.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "task.created.queue";

    @Bean
    public DirectExchange taskExchange() {
        return new DirectExchange(TaskMessageProducer.EXCHANGE);
    }

    @Bean
    public Queue taskQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Binding binding(Queue taskQueue, DirectExchange taskExchange) {
        return BindingBuilder.bind(taskQueue).to(taskExchange).with(TaskMessageProducer.ROUTING_KEY);
    }
}
